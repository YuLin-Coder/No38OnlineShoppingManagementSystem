<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>top</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<style type="text/css">
body {
	background: #DD2727;
	margin: 0px;
	color: #ffffff;
}

a {
	text-decoration: none;
	color: #ffffff;
	font-weight: 900;
}

a:hover {
	text-decoration: underline;
	color: #ffffff;
	font-weight: 900;
}
</style>
</head>

<body>
	<h1 style="text-align: center;">网上购物系统</h1>
	<div style="font-size: 10pt; line-height: 10px;">

		<%-- 根据用户是否登录，显示不同的链接 --%>
		<c:choose>
			<c:when test="${empty sessionScope.sessionUser }">
				<a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">亲，请登录</a> |&nbsp; 
		  <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">免费注册</a>
			</c:when>
			<c:otherwise>
		      用户：${sessionScope.sessionUser.loginname }&nbsp;&nbsp;|&nbsp;&nbsp;
		  
<!-- 		  <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
				<a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="<c:url value='/index.jsp'/>" target="_top">|&nbsp;&nbsp;主页</a>&nbsp;&nbsp;&nbsp;&nbsp;
		   <a href="<c:url value='/CartItemServlet?method=myCart'/>"
					target="body">|我的购物车</a>&nbsp;&nbsp;|&nbsp;
		  <a href="<c:url value='/OrderServlet?method=myOrders'/>"
					target="body">我的订单</a>&nbsp;&nbsp;|&nbsp;
		   <a href="<c:url value='/jsps/userImfo/main.jsp'/>" target="_parent">我的中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;
<!-- 		   <select name="select" onchange="window.open(this.options[this.selected].value,target='_self')"> -->
				<!-- 				<select name="S1" onChange="window.open(this.value),target='_top'"> -->

				<c:choose>

					<c:when test="${sessionScope.sessionUser.isStore==0}">
						<a href="<c:url value='/storejsps/main2.jsp'/>"
							target="body">免费开店</a>
					</c:when>

					<c:otherwise>
					<a href="<c:url value='/storejsps/store/ownStore/main.jsp'/>"
							target="_parent">我的店铺</a>
					|&nbsp;
					<a href="<c:url value='/storejsps/store/index.jsp'/>"
							target="_parent">个人店铺管理</a>
					</c:otherwise>
					
					
				</c:choose>

			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
<%-- 
						<select name="phpddt"
							onchange="window.open(this.options[this.selectedIndex].value,'_top')"
							size=1>
							<option value="">请选择</option>
							<!-- 					<option value='${request.Context}/jsps/userImfo/main.jsp' selected>卖家中心</option> -->
							<option
								value='${pageContext.request.contextPath}/storejsps/main.jsp'
								selected>卖家中心</option>
							<option
								value='${pageContext.request.contextPath}/storejsps/main.jsp'>免费开店</option>
							<option
								value='${pageContext.request.contextPath}/storejsps/main.jsp'>已卖出宝贝</option>
							<option
								value='${pageContext.request.contextPath}/storejsps/main.jsp'>出售中宝贝</option>
						</select>
--%>