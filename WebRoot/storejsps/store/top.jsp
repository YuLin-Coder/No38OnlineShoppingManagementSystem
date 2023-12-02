<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
    <base target="body">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {font-size: 10pt;}
	a {color: #aaa;}
</style>
  </head>
  
  <body style="background: rgb(78,78,78);color: #fff;">
<h1 style="text-align: center; line-height: 30px;">我的店铺</h1>
<div style="line-height: 10px;">
	<span>店商：${sessionScope.sessionUser.loginname }</span>
	<a target="_top" href="<c:url value='/UserServlet?method=quit'/>">退出</a>
	<span style="padding-left:50px;">
	    <a href="<c:url value='/jsps/main2.jsp'/>">主页</a>
		<a href="<c:url value='/storejsps/store/storeImfo/storeImfo.jsp'/>">店铺信息管理</a>
		<a href="<c:url value='/storejsps/store/storeCategroy/main.jsp'/>">商品管理</a>
		<a href="<c:url value='/store/StoreOrderServlet?method=findAll'/>">顾客订单管理</a>
	</span>
</div>
  </body>
</html>
