<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户信息列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/adminjsps/admin/user/css/list.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/css.css'/>">

<link rel="stylesheet" type="text/css"
	href="<c:url value='/jsps/css/goods/list.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/jsps/pager/pager.css'/>" />
<script type="text/javascript"
	src="<c:url value='/jsps/pager/pager.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
		<script type="text/javascript">
		function checkForm() {
			if(!$("#loginname").val()) {
				alert("查询名不能为空！");
				return false;
			}
			return true;
		}
	</script>
</head>

<body>
	<h2 style="text-align: center;">用户信息列表</h2>

	<div align="center">
		<form action="<c:url value='/admin/AdminUserServlet'/>" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="method" value="selectUserByName" /> 
			<%-- <input type="hidden" name="pb" value="${pb}"/>--%>
			<input type="text" name="loginname"  id="loginname"/> <input type="submit" value="按用户名搜索" />
		</form>
	</div>
	<div align="center">
	<h3><a href="<c:url value='/adminjsps/admin/user/add.jsp'/>">添加用户</a></h3>
	</div>
	<c:if test="${! empty msg }">
	<h3 align="center" style="color:red">搜索结果：${msg }</h3>
	</c:if>
	
	<c:if test="${!empty userSearch}">
	<table align="center" border="1" cellpadding="0" cellspacing="0">
		<tr class="trTitle">
			<th width="150px;">用户id</th>
			<th width="150px;">用户登录名</th>
			<th width="150px;">用户登录密码</th>
			<th width="150px;">用户邮箱</th>
			<th width="150px;">用户是否开店</th>
			<th width="150px;">管理操作</th>
		</tr>
			<tr class="trOneLevel">
				<td width="150px;">${userSearch.uid}</td>
				<td width="150px;">${userSearch.loginname }</td>
				<td width="150px;">${userSearch.loginpass }</td>
				<td width="150px;">${userSearch.email }</td>
				<td width="150px;">${userSearch.isStore}</td>
				<td width="150px;"><a
					href="<c:url value='/admin/AdminUserServlet?method=editUserByIdPre&uid=${userSearch.uid }'/>">修改</a>
					<a onclick="return confirm('您是否真要删除该用户？')"
					href="<c:url value='/admin/AdminUserServlet?method=deleteUserById&uid=${userSearch.uid }'/>">删除</a>
				</td>
			</tr>
	</table>
	</c:if>
	
	<c:if test="${! empty pb }">
	<table align="center" border="1" cellpadding="0" cellspacing="0">
		<tr class="trTitle">
			<th width="150px;">用户id</th>
			<th width="150px;">用户登录名</th>
			<th width="150px;">用户登录密码</th>
			<th width="150px;">用户邮箱</th>
			<th width="150px;">用户是否开店</th>
			<th width="150px;">管理操作</th>
		</tr>

		<c:forEach items="${pb.beanList }" var="user">
			<%-- c:forEach items="${users }" var="user">      --%>
			<tr class="trOneLevel">
				<td width="150px;">${user.uid}</td>
				<td width="150px;">${user.loginname }</td>
				<td width="150px;">${user.loginpass }</td>
				<td width="150px;">${user.email }</td>
				<%-- <td width="150px;">${user.isStore}</td>--%>
			
    		<c:choose>
    		<c:when test="${user.isStore==1 }">
    		<td width="200px;">已经开店 &nbsp;&nbsp;&nbsp;&nbsp;</td>
    		
    		<%--	<td width="200px;">已经开店 &nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value='/admin/AdminUserServlet?method=deleteStoreById&uid=${user.uid }'/>">封店</a></td> --%>
    		</c:when>
    		
    		<c:otherwise>                                           
    		<td width="200px;">未开店  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value='/admin/AdminUserServlet?method=addStorePre&uid=${user.uid }'/>">开店</a></td>
    		</c:otherwise>
    		
    		</c:choose>
    	
				<td width="150px;"><a
					href="<c:url value='/admin/AdminUserServlet?method=editUserByIdPre&uid=${user.uid }'/>">修改</a>
					<a onclick="return confirm('您是否真要删除该用户？')"
					href="<c:url value='/admin/AdminUserServlet?method=deleteUserById&uid=${user.uid }'/>">删除</a>
				</td>
			</tr>
		</c:forEach>
		
	</table>
</c:if>
	<div style="float:left; width: 100%; text-align: center;">
		<hr />
		<br />
		<%@include file="/jsps/pager/pager.jsp"%>
	</div>
</body>
</html>
