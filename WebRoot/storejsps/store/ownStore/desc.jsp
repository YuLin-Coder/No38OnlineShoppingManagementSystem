<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>商品详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">

	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
<!--     <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script> -->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/goods/desc.css'/>">
	<script src="<c:url value='/jsps/js/goods/desc.js'/>"></script>
  </head>
  
  <body>
  <div class="divGoodsName">${goods.gname }</div>
  <div>
    <img align="top" src="<c:url value='/${goods.image_w }'/>" class="img_image_w"/>
    <div class="divGoodsDesc">
	    <ul>
	    	<li>商品编号：${goods.gid }</li>
	    	<li>现价：<span class="price_n">&yen;${goods.currPrice }</span></li>
	    	<li>定价：<span class="spanPrice">&yen;${goods.price }</span>　折扣：<span style="color: #c30;">${goods.discount }</span>折</li>
	    </ul>
		<hr class="hr1"/>
		<table>
			<tr>
				<td colspan="3">
					商品描述：${goods.description } 
				</td>
			</tr>
			<tr>
				<td colspan="3">
					商品产地：${goods.pro_area }
				</td>
			</tr>
			<tr>
				<td colspan="3"><a href="<c:url value='/store/StoreCategoryServlet?method=findStoreByName&sid=${goods.store.sid }' />"  target="_parent">所属店铺：${goods.store.sname }</a></td>
			</tr>

		</table>
		
		<div class="divForm">
			<form id="form1" action="<c:url value='/CartItemServlet'/>" method="post">
				<input type="hidden" name="method" value="add"/>
				<input type="hidden" name="gid" value="${goods.gid }"/>
				<input type="hidden" name="sid" value="${goods.store.sid }"/>
  				我要买：<input id="cnt" style="width: 40px;text-align: center;" type="text" name="quantity" value="1"/>${goods.units}
  			</form>
  			<a id="btn" href="javascript:$('#form1').submit();"></a>
  		</div>
  		
	</div>
  </div>
  </body>
</html>
