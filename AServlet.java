package cn.hytc.wx;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class AServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		//1.从GET请求中获取code参数
		String code = request.getParameter("code");
		
		//2.通过code换取网页授权access_token和openid
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe1e3696881c14c1d&" +
					 "secret=cef0895ae1acbf624ad6057916da0da9&code="+code+"&grant_type=authorization_code";
		JSONObject json = doGetJson(url);
		String access_token = json.getString("access_token");
		String openid = json.getString("openid");
		
		//3.通过网页授权access_token和openid获取用户基本信息
		String userInfoURL = "https://api.weixin.qq.com/sns/userinfo?access_token="+(access_token)+"&openid="+openid+"&lang=zh_CN";
		JSONObject userInfo = doGetJson(userInfoURL);
		String nickname = userInfo.getString("nickname");
		String headimgurl = userInfo.getString("headimgurl");
		
		//4.向微信客户端响应用户信息
		response.getWriter().println("<h2>用户名:"+nickname+"</h2><br/>");
		response.getWriter().println("<h2>头像:</h2><br/><img src="+headimgurl+" width=132 height=132/>");
	}

	//向指定目标地址发送请求，并接受返回的json数据
    public JSONObject doGetJson(String url) throws ClientProtocolException, IOException{
        JSONObject jsonObject = null;
        //首先初始化HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();
        //通过get方式进行提交
        HttpGet httpGet = new HttpGet(url);
        //通过HTTPclient的execute方法进行发送请求
        HttpResponse response = client.execute(httpGet);
        //从response里面拿自己想要的结果
        HttpEntity entity = response.getEntity();
        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        //把链接释放掉
        httpGet.releaseConnection();
        return jsonObject;
    }

}
