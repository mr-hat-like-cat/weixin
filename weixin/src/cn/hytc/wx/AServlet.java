package cn.hytc.wx;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 负责与微信服务器交互
 * @author Administrator
 *
 */
@SuppressWarnings("all")
@ServerEndpoint("/echo")
public class AServlet extends HttpServlet 
{
	static String nickname =null;
	static String headimgurl = null;
	static JSONObject json = null;
	static JSONObject userInfo = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html;charset=utf-8");
		
		//1.从GET请求中获取code参数
		String code = request.getParameter("code");
		
		//2.通过code换取网页授权access_token和openid
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe1e3696881c14c1d&" +
					 "secret=cef0895ae1acbf624ad6057916da0da9&code="+code+"&grant_type=authorization_code";
		json = doGetJson(url);
		String access_token = null;
		if( json.getString("access_token") != null ) 
		{
			access_token = json.getString("access_token");
		}
		
		String openid = json.getString("openid");
		
		
		//3.通过网页授权access_token和openid获取用户基本信息
		String userInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token="+(access_token)+"&openid="+openid+"&lang=zh_CN";
		userInfo = doGetJson(userInfoURL);
		nickname = userInfo.getString("nickname");
		headimgurl = userInfo.getString("headimgurl");
		
		//4.向微信客户端响应用户信息
		try {
			response.getWriter().println("<h3>登陆成功</h3>");
		} catch (IOException e) {
			throw new RuntimeException();
		}
//		response.getWriter().println("<h2>用户名:"+nickname+"</h2><br/>");
//		response.getWriter().println("<h2>头像:</h2><br/><img src="+headimgurl+" width=132 height=132/>");
	}

	//向指定目标地址发送请求，并接受返回的json数据
    public JSONObject doGetJson(String url) 
    {
        JSONObject jsonObject = null;
        //首先初始化HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();
        //通过get方式进行提交
        HttpGet httpGet = new HttpGet(url);
        //通过HTTPclient的execute方法进行发送请求
        HttpResponse response;
		try 
		{
			response = client.execute(httpGet);
	        //从response里面拿自己想要的结果
	        HttpEntity entity = response.getEntity();
	        if(entity != null)
	        {
	            String result = EntityUtils.toString(entity,"UTF-8");
	            jsonObject = JSONObject.fromObject(result);
	        }
	        //把链接释放掉
	        httpGet.releaseConnection();
		}  catch (Exception e) 
		{
			throw new RuntimeException();
		}
        return jsonObject;
    }
	@OnOpen
	public void open(Session session) {}
	@OnMessage
	public void message(final Session session,final String msg)
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run() { 
				boolean isGet = false;	//用于判断是否已经获得userInfo，没有则进入循环，有则不进入循环
				while(true) {
					if( nickname != null && headimgurl != null && !isGet ) {
						isGet = true;
						try {
							session.getBasicRemote().sendText(nickname+"|"+headimgurl);
						} catch (IOException e) {
							throw new RuntimeException();
						}
						//由于在第一次扫码后，用户信息就已经得到
						//此后无论websocket建立多少次连接，都不需要再扫码，就可以得到useInfo(关闭服务器，或者浏览器除外)
						//这显然不是我们想要的，我们必须确保用户点击退出或者刷新页面后
						//安全退出；因此我们需要在传递userInfo给页面后，就将其置为null
						//这样每次退出，或者刷新页面就都需要再次授权登陆
						nickname = null;
						headimgurl = null;
						break;
					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						throw new RuntimeException();
					}
				}
			}
 		});
		t.start();	
	}
	@OnClose
	public void close(Session session) {}
}
