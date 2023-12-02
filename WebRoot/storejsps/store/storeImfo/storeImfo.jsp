<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>个人资料</title>

</head>

<body>
		<table>
			<tr>
				<td>
					<%--<image src="<c:url value='/images/touxiang.jpg'/>"></image> --%>
					<%-- <img  src="../../images/touxiang.jpg" alt="">--%> <img
					src="/shopping/images/touxiang.jpg" alt="">
				</td>
				<td>
		
				</td>
				<td colspan="15" rowspan="15"><img
					src="/shopping/images/meitu.jpg" alt="" align="right"></td>
			</tr>

			<tr>
				<td>用户名：${sessionScope.sessionUser.loginname}</td>
			</tr>

			<tr>
				<td>账户安全 60分</td>
			</tr>

			<tr>
				<td>我的姓名：${sessionScope.sessionUser.loginname}</td>
			</tr>

			<tr>
				<td>我的密码：${sessionScope.sessionUser.loginpass}</td>
			</tr>

			<tr>
				<td>我的邮箱：${sessionScope.sessionUser.email}</td>
			</tr>

			<tr>
				<td>收货地址：中国北京 
				</td>
			</tr>
		</table>
</body>

</html>