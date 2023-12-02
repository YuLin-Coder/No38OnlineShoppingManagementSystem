<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#adminname").val()) {
				alert("管理员名称不能为空！");
				return false;
			}
			if(!$("#adminpwd").val()) {
				alert("管理员密码不能为空！");
				return false;
			}
			return true;
		}
	</script>
  </head>
  
  <body>
 <div align="center">
  
<h1>管理员登录页面</h1>
<hr/>
  <p style="font-weight: 900; color: red">${msg }</p>
<form action="<c:url value='/admin/AdminServlet'/>" method="post" onsubmit="return checkForm()" target="_top">
	<input type="hidden" name="method" value="login"/>
	<table>
	<tr>
	<td>管理员账户：</td>
	<td><input type="text" name="adminname" value="" id="adminname"/></td>
	</tr>
	<tr>
	<td>密　　　码：</td>
	<td><input type="password" name="adminpwd" id="adminpwd"/></td>
	</tr>
	<tr>
	
	<td colspan="2" align="center">
	<input type="submit" value="登录"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="重置" />
<!-- 	<input type="button" value="返回" onclick="history.go(-1)"/> -->
	</td>
	</tr>
	</table>
</form>

</div>
  </body>
</html>
