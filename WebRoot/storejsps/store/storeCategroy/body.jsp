<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'body.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">
		function checkForm() {
			if(!$("#gname").val()) {
				alert("查询名不能为空！");
				return false;
			}
			return true;
		}
	</script>

</head>

<body>
	<h1 align="center">商品管理</h1>
	<div align="center">
   <a href="<c:url value='/store/StoreGoodsServlet?method=addPre&uid=${sessionScope.sessionUser.uid }'/>"
			style="margin: 20px; font-size: 20px;">添加商品</a>
	<h1></h1>
	
	<form action="<c:url value='/store/StoreGoodsServlet'/>" method="post"
		onsubmit="return checkForm()">
		
		<input type="hidden" name="method" value="StorefindByGnameAndUid" /> 
		<input type="hidden" name="uid" value="${sessionScope.sessionUser.uid }" />
		<input type="text" name="gname" id="gname" /> <input type="submit" value="按商品名搜索" />
	</form>
	</div>
</body>
</html>
