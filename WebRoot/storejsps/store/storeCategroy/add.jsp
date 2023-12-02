<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'bookdesc.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/storejsps/store/css/goods/add.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/jquery/jquery.datepick.css'/>">
<script type="text/javascript"
	src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
<script type="text/javascript">
	$(function() {
		$("#btn").addClass("btn1");
		$("#btn").hover(function() {
			$("#btn").removeClass("btn1");
			$("#btn").addClass("btn2");
		}, function() {
			$("#btn").removeClass("btn2");
			$("#btn").addClass("btn1");
		});

		$("#btn").click(
				function() {
					var gname = $("#gname").val();
					var currPrice = $("#currPrice").val();
					var price = $("#price").val();
					var discount = $("#discount").val();
					var description = $("#description").val();
					var pro_area = $("#pro_area").val();
					var units = $("#units").val();
					var pid = $("#pid").val();
					var cid = $("#cid").val();
					var image_w = $("#image_w").val();
					var image_b = $("#image_b").val();

					if (!gname || !currPrice || !price || !discount
							|| !description || !pro_area || !units || !pid
							|| !cid || !image_w || !image_b) {
						alert("商品名、当前价、定价、折扣、描述、产地、单位、所属类别、商品子类别、大图、小图都不能为空！");
						return false;
					}

					if (isNaN(currPrice) || isNaN(price) || isNaN(discount)) {
						alert("当前价、定价、折扣必须是合法小数！");
						return false;
					}
					$("#form").submit();
				});
	});
</script>
</head>

<body>
	<div>
		<p style="font-weight: 900; color: red;">${msg }</p>
		<form action="<c:url value='/store/StoreGoodsAddServlet'/>" enctype="multipart/form-data" method="post" id="form">
			<input type="hidden" name="sid" value="${store.sid }"/>
			<div>
				<ul>
					<li>商品名称： <input id="gname" type="text" name="gname"
						value="Java从入门到精通" style="width:500px;" /></li>
					<li>大图： <input id="image_w" type="file" name="image_w" /></li>
					<li>小图： <input id="image_b" type="file" name="image_b" /></li>
					<li>当前价：<input id="currPrice" type="text" name="currPrice"
						value="40.7" style="width:50px;" /></li>
					<li>定价： <input id="price" type="text" name="price"
						value="59.0" style="width:50px;" /> 折扣：<input id="discount"
						type="text" name="discount" value="6.9" style="width:30px;" />折
					</li>
				</ul>
				<hr style="margin-left: 50px; height: 1px; color: #dcdcdc" />
				<table>
					<tr>
						<td colspan="3">商品描述： <input type="text" id="description"
							name="description" value="java编程书" style="width:150px;" />
						</td>
					</tr>
					<tr>
						<td colspan="3">商品产地： <input type="text" name="pro_area"
							id="pro_area" value="中国北京" style="width:200px;" />
						</td>
					</tr>
					<tr>
						<td colspan="3">单位： <input type="text" name="units"
							id="units" value="本" style="width:100px;" />
						</td>
					</tr>
					<tr>
						<td>所属类别：<input type="text" name="pid" id="pid"
							value="${sessionScope.parents.cname }" disabled="disabled"
							style="width:200px;" />
						</td>
						<td>所属子类别：<select name="cid" id="cid">
								<option value="">====请选择商品子类别====</option>
								
<c:forEach items="${sessionScope.parents.children }" var="parent" >
							<option value="${parent.cid }">${parent.cname }</option>
</c:forEach>
						</select></td>
						<td></td>
					</tr>
					<tr>
						<td>店铺名称：<input type="text" id="sname" class="sname"
							value="${store.sname }"  disabled="disabled"></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td><input type="button" id="btn" class="btn" value="商品上架">
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
		</form>
	</div>

</body>
</html>
