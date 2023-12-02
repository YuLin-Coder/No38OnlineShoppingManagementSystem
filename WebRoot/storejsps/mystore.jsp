<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<title>My JSP 'mystore.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- <script type="text/javascript" -->
<script type="text/javascript"
	src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>

</head>

<body>
	<h1>我要开店</h1>
	<form target="_parent" action="<c:url value='/store/StoreServlet'/>" method="post">
		<input type="hidden" name="method" value="creatStore"/>
		<input type="hidden" name="uid" value="${sessionScope.sessionUser.uid}"/>
		<table>
			<tr>
				<td>店铺名称：</td>
				<td><input type="text" name="sname"></td>
			</tr>
			<tr>
				<td>开店类型：</td>
				<td><select name="cid" id="cid" class="cid">
						<option value="">==请选择开店类型==</option>
						<c:forEach items="${parents }" var="parent">
							<option value="${parent.cid }">${parent.cname }</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td align="center" colspan="2"><input type="submit" value="开店">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>


<%-- 
<!-- 	src="<c:url value='/storejsps/store/js/store/mystore.js'/>"></script> -->
	
<!-- <script type="text/javascript"> -->
<!--   function myjquery() { -->
 
<!-- 	$.ajax({ -->
<!-- 		async:true, -->
<!-- 		cache:false, -->
<!-- 		url:"${pageContext.request.contextPath}/store/StoreCategoryServlet", -->
<!-- 		data:{method:"ajaxFindParents"}, -->
<!-- 		type:"POST", -->
<!-- 		dataType:"json", -->
<!-- 		success:function(arr) { -->
<!-- 		 alert("this is a ajax request script test"); -->
<!-- 			// 得到cid，删除它的内容 -->
<!-- 			$("#cid").empty();//删除元素的子元素 -->
<!-- 			$("#cid").append($("<option>==请选择开店类型==</option>"));//添加头 -->
<!-- 			// 循环遍历数组，把每个对象转换成<option>添加到cid中 -->
<!-- 			for(var i = 0; i < arr.length; i++) { -->
				
<!-- 				var option = $("<option>").val(arr[i].cid).text(arr[i].cname); -->
<!-- 				$("#cid").append(option); -->
<!-- 			} -->
<!-- 		} -->
<!-- 	});	 -->
<!-- } -->
<!-- </script> -->
	
--%>

<%-- 
			<tr>
				<td>开店类型：</td>
				<td><select name="phpddt"
					onchange="window.open(this.options[this.selectedIndex].value,'_top')"
					size=1>
						<option value="">请选择</option>
						<option
							value='${pageContext.request.contextPath}/storejsps/main.jsp'
							selected>卖家中心</option>
						<option
							value='${pageContext.request.contextPath}/storejsps/main.jsp'>免费开店</option>
						<option
							value='${pageContext.request.contextPath}/storejsps/main.jsp'>已卖出宝贝</option>
						<option
							value='${pageContext.request.contextPath}/storejsps/main.jsp'>出售中宝贝</option>
				</select></td>
			</tr>

--%>