package cn.hytc.wx;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接入微信公众平台
 * @author Administrator
 *
 */
@WebServlet("/wx")
public class WXServlet extends HttpServlet{
	//1.开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上
	//2.原样返回echostr参数内容，即可接入成功
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取GET请求中的echostr参数
		String echostr = request.getParameter("echostr");
		//原样返回echostr参数
		response.getWriter().print(echostr);
	}

}
