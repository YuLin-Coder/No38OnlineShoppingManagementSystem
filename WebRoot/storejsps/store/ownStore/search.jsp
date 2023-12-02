<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>按名称查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		margin-top: 5px;
		margin-bottom: 0px;
		margin-left:200px;
		color: #404040;
	}
	input {
		width: 300px;
		height: 30px;
		border-style:solid;
		margin:0px;
		border-color: #15B69A;
	}
	a {
		text-transform:none;
		text-decoration:none;
		border-width: 0px;
	} 
	a:hover {
		text-decoration:underline;
		border-width: 0px;
	}
	span {
		margin: 0px;
	}
</style>

<script type="text/javascript">
	function checkForm() {
		if (!$("#check").val()) {
			alert("搜索名不能为空！");
			return false;
		}
		return true;
	}
</script>
  </head>
  
  <body>
    <form action="<c:url value='/GoodsServlet'/>" method="get" target="body" id="form1" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value="findByGname"/>
    	<input type="text" name="gname"/>
    	<span >
    		<a href="javascript:document.getElementById('form1').submit();" id="check"><img align="top"  border="0" src="../../../images/btn_search.jpg"/></a>
    	</span>
    </form>
    
  </body>
</html>
