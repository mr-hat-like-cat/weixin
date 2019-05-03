<%@ page language="java" import="java.util.*,java.net.URLEncoder" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>weixin</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">     
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="css/index.css">
		<script src="js/qrious.js"></script>
		<script src="js/jquery-2.2.2.js"></script>
		<script src="js/index.js"></script>
  </head>
  <body>
		<!-- S = top -->
		<div class="top">
			<div class="logo"><s></s></div>
			<div class="webName">花生米</div>
		</div>
		<!-- E = top -->
		<!-- S = nav-->
		<div class="nav">
			<ul>
				<li>总览</li>
				<hr/>
				<li><a href="javascript:void(0)" class="sub">关注一下</a></li>
				<li><a href="javascript:void(0)" class="sub">微信登陆</a></li>
			</ul>

		</div>
		<!-- E = nav -->
		<!-- S = content -->
		<div class="content">
			<div class="attention">
				<p>注意：由于本网站使用的是测试公众号</p>
				<p>&nbsp;&nbsp;微信登陆前，务必先关注测试公众号</p>
			</div>
			<div class="content_s"></div>
			<img src="" id="qrious">
		</div>
		<!-- E = content -->
  </body>
</html>

