<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员添加店铺</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'/>">

    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/adminjsps/admin/store/js/add.js'/>"></script>
  </head>
  
  <body>

<div id="divMain">
  <div id="divTitle">
    <span id="spanTitle">管理员添加店铺</span>
  </div>
  <div id="divBody">
<form action="<c:url value='/admin/AdminUserServlet'/>" method="post">
	<input type="hidden" name="method" value="addStore"/> 
	<input type="hidden" name="uid" value="${uid }"/>
	
    <table id="tableForm">
      <tr>
        <td class="tdText">店铺名：</td>
        <td class="tdInput">
          <input class="inputClass" type="text" name="sname" id="sname" value="${store.sname }"/>
        </td>
        <td class="tdError">
          <label class="errorClass" id="snameError">${errors.sname }</label>
        </td>
      </tr>
        <tr>
        <td class="tdText">开店类型：</td>
       	<td><select name="cid" id="cid" class="cid">
				<option value="">==请选择开店类型==</option>
				<c:forEach items="${sessionScope.parents }" var="parent">
					<option value="${parent.cid }">${parent.cname }</option>
				</c:forEach>
		</select>
		</td>
		<td>
		<label class="errorClass" id="cidError">${errors.cid }</label>
		</td>
      </tr>
       <tr>
        <td></td>
        	<td align="center" ><input type="submit" value="添加店铺">
				</td>

        <td>
          <label></label>
        </td>
      </tr>
    </table>
</form>    
  </div>
</div>
  </body>
</html>