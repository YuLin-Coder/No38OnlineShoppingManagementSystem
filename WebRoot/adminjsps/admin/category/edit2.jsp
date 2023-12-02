<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改分类</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript"
	src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript">
	function checkForm() {
		if (!$("#cname").val()) {
			alert("分类名不能为空！");
			return false;
		}
		if (!$("#pid").val()) {
			alert("一级分类不能为空！");
			return false;
		}
		if (!$("#desc").val()) {
			alert("分类描述不能为空！");
			return false;
		}
		return true;
	}
</script>

</head>

<body>
	<div align="center">
		<h3>修改2级分类</h3>
		<h1></h1>
		<p style="font-weight: 900; color: red">${msg }</p>
		<form action="<c:url value='/admin/AdminCategoryServlet'/>"
			method="post" onsubmit="return checkForm()">
			<input type="hidden" name="method" value="editChild" /> <input
				type="hidden" name="cid" value="${child.cid }" />
			<table>
				<tr>
					<td>分类名称：</td>
					<td><input type="text" name="cname" value="${child.cname }"
						id="cname" /></td>
				</tr>
				<tr>
					<td>一级分类：</td>
					<td><select name="pid" id="pid">
							<option value="">===选择1级分类===</option>
							<c:forEach items="${parents }" var="parent">
								<option value="${parent.cid }"
									<c:if test="${parent.cid eq child.parent.cid }">selected="selected"</c:if>>${parent.cname }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>分类描述：</td>
					<td><textarea rows="5" cols="50" name="desc" id="desc">${child.desc }</textarea></td>
				</tr>
				<tr>
					<td><input type="submit" value="修改二级分类" /></td>
					<td><input type="button" value="&nbsp;点击返回&nbsp;" onclick="history.go(-1)" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
