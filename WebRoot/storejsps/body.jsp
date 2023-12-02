<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="">
    
    <title>body</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
a {text-decoration: none;}
</style>
  </head>
  
  <body>
    <div align="center">
    <h1>免费开店</h1>
     <img src="/shopping/images/personStore.jpg" alt="">
      <form target="body" action="<c:url value='/store/StoreCategoryServlet?method=ajaxFindParents'/>" method="post">
     <p><input type="submit" value="创建个人店铺"></p>
     </form>
     </div>
  </body>
</html>
