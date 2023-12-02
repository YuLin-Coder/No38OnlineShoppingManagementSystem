<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
  <script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
  <script src="<c:url value='/jsps/js/cart/list.js'/>"></script>
  </head>
  
  <body>
   <c:choose>
	<c:when test="${empty cartItemList }">
	<table width="95%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td align="right">
				<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
			</td>
			<td>
				<span class="spanEmpty">您的购物车中暂时没有商品</span>
			</td>
		</tr>
	</table>  
	</c:when>
	<c:otherwise>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr align="center" bgcolor="#efeae5">
		<td align="left" width="50px">
			<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
		</td>
		<th>店铺</th>
		<th colspan="2">商品名称</th>
		<th>单价</th>
		<th>数量</th>
		<th>小计</th>
		<th>操作</th>
	</tr>


<c:forEach items="${cartItemList }" var="cartItem">
	<tr align="center">
		<td align="left">
			<input value="${cartItem.cartItemId }" type="checkbox" name="checkboxBtn" checked="checked"/>
		</td>
		<td align="left" width="70px">
		    <a href="<c:url value='/store/StoreCategoryServlet?method=findStoreByName&sid=${cartItem.store.sid }'/>" target="_parent"><span>${cartItem.store.sname }</span></a>
		</td>
		<td align="left" width="70px">
			<a class="linkImage" href="<c:url value='/GoodsServlet?method=load&gid=${cartItem.goods.gid }'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.goods.image_b }'/>"/></a>
		</td>
		<td align="left" width="200px">
		    <a href="<c:url value='/GoodsServlet?method=load&gid=${cartItem.goods.gid }'/>"><span>${cartItem.goods.gname }</span></a>
		</td>
		<td><span>&yen;<span class="currPrice">${cartItem.goods.currPrice }</span></span></td>
		<td>
			<a class="jian" id="${cartItem.cartItemId }Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartItemId }Quantity" type="text" value="${cartItem.quantity }"/><a class="jia" id="${cartItem.cartItemId }Jia"></a>
		</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartItemId }Subtotal">${cartItem.subtotal }</span></span>
		</td>
		<td>
			<a href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.cartItemId }'/>">删除</a>
		</td>
	</tr>
</c:forEach>


















	
	<tr>
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDelete();">批量删除</a>
		</td>
		<td colspan="4" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
		</td>
	</tr>
	<tr>
		<td colspan="8" align="right">
			<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	<form id="jieSuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="total" id="hiddenTotal"/>
		<input type="hidden" name="method" value="loadCartItems"/>
	</form>

	</c:otherwise>
</c:choose>
  </body>
</html>
