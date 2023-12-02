<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>店铺信息列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/adminjsps/admin/Store/css/list.css'/>">
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
			if(!$("#sname").val()) {
				alert("查询名不能为空！");
				return false;
			}
			return true;
		}
	</script>
</head>

<body>
	<h2 style="text-align: center;">店铺信息列表</h2>

	<div align="center">
		<form action="<c:url value='/admin/AdminStoreServlet'/>" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="method" value="selectStoreByName" /> 
			<%-- <input type="hidden" name="pb" value="${pb}"/>--%>
			<input type="text" name="sname"  id="sname"/> <input type="submit" value="按店铺名搜索" />
		</form>
	</div>
	<div align="center">
	<h3><a href="<c:url value='/admin/AdminStoreServlet?method=addStorePre'/>">添加店铺</a></h3>
	</div>
	<c:if test="${! empty msg }">
	<h3 align="center" style="color:red">搜索结果：${msg }</h3>
	</c:if>
	
	<c:if test="${!empty storeSearch}">
	<table align="center" border="1" cellpadding="0" cellspacing="0">
		<tr class="trTitle">
			<th width="150px;">店铺id</th>
			<th width="150px;">店铺名</th>
			<th width="150px;">店铺所属用户</th>
			<th width="150px;">开店类型</th>
			<th width="150px;">管理操作</th>
		</tr>
			<tr class="trOneLevel">
				<td width="150px;">${storeSearch.sid}</td>
				<td width="150px;">${storeSearch.sname }</td>
				<td width="150px;">${storeSearch.user.loginname }</td>
				<td width="150px;">${storeSearch.category.cname }</td>
				<td width="150px;"><a
					href="<c:url value='/admin/AdminStoreServlet?method=editStoreByIdPre&uid=${storeSearch.sid }'/>">修改</a>
					<a onclick="return confirm('您是否真要删除该店铺？')"
					href="<c:url value='/admin/AdminStoreServlet?method=deleteStoreById&uid=${storeSearch.sid }'/>">删除</a>
				</td>
			</tr>
	</table>
	</c:if>
	
	<c:if test="${! empty pb }">
	<table align="center" border="1" cellpadding="0" cellspacing="0">
		<tr class="trTitle">
			<th width="150px;">店铺id</th>
			<th width="150px;">店铺名</th>
			<th width="150px;">店铺所属用户</th>
			<th width="150px;">开店类型</th>
			<th width="150px;">管理操作</th>
		</tr>

		<c:forEach items="${pb.beanList }" var="Store">
			<%-- c:forEach items="${Stores }" var="Store">      --%>
			<tr class="trOneLevel">
				<td width="150px;">${Store.sid}</td>
				<td width="150px;">${Store.sname }</td>
				<td width="150px;">${Store.user.loginname }</td>
				<td width="150px;">${Store.category.cname }</td>
				<td width="150px;"><a
					href="<c:url value='/admin/AdminStoreServlet?method=editStoreByIdPre&sid=${Store.sid }'/>">修改</a>
					<a onclick="return confirm('您是否真要删除该店铺？')"
					href="<c:url value='/admin/AdminStoreServlet?method=deleteStoreById&sid=${Store.sid }'/>">删除</a>
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
