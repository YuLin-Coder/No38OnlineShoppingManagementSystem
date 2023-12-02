<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生成订单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/showitem.css'/>">
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<style type="text/css">
#addr{width: 500px; height: 32px;border: 1px solid #7f9db9; padding-left: 10px; line-height: 32px;}
</style>

<script type="text/javascript">
	//计算合计
	$(function() {
		var total = 0;
		$(".subtotal").each(function() {
			total += Number($(this).text());
		});
		$("#total").text(round(total, 2));
	});
/*
 * 保留几位小数
 */
function round(num,dec){ 
    var strNum = num + '';/*把要转换的小数转换成字符串*/
    var index = strNum.indexOf("."); /*获取小数点的位置*/
    if(index < 0) {
        return num;/*如果没有小数点，那么无需四舍五入，返回这个整数*/
    }
    var n = strNum.length - index -1;/*获取当前浮点数，小数点后的位数*/
    if(dec < n){ 
    	/*把小数点向后移动要保留的位数，把需要保留的小数部分变成整数部分，只留下不需要保留的部分为小数*/ 
        var e = Math.pow(10, dec);
        num = num * e;
        /*进行四舍五入，只保留整数部分*/
        num = Math.round(num);
        /*再把原来小数部分还原为小数*/
        return num / e;
    } else { 
        return num;/*如果当前小数点后的位数等于或小于要保留的位数，那么无需处理，直接返回*/
    } 
} 
</script>
  </head>
  
  <body>
  <c:choose>
  	<c:when test="${empty cartItemList }">购物车里没有商品！</c:when>
  	<c:otherwise>
<form id="form1" action="<c:url value='/OrderServlet'/>" method="post">
	<input type="hidden" name="cartItemIds" value="${cartItemIds }"/>
	<input type="hidden" name="method" value="createOrder"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr bgcolor="#efeae5">
		<td width="400px" colspan="6"><span style="font-weight: 900;">生成订单</span></td>
	</tr>
	<tr align="center">
	    <td>店铺</td>
		<td colspan="2">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>


<c:forEach items="${cartItemList }" var="cartItem">
	<tr align="center">
	    <td align="center" width="100px">
		    <a href="<c:url value='/store/StoreCategoryServlet?method=findStoreByName&sid=${cartItem.store.sid }'/>" target="_parent"><span>${cartItem.store.sname }</span></a>
		</td>
		<td align="right" width="100px">
			<a class="linkImage" href="<c:url value='/GoodsServlet?method=load&gid=${cartItem.goods.gid }'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.goods.image_b }'/>"/></a>
		</td>
		<td align="left" width="200px">
			<a href="<c:url value='/GoodsServlet?method=load&gid=${cartItem.goods.gid }'/>"><span>${cartItem.goods.gname }</span></a>
		</td>
		<td>&yen;${cartItem.goods.currPrice }</td>
		<td>${cartItem.quantity }</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subtotal">${cartItem.subtotal }</span></span>
		</td>
	</tr>
</c:forEach>
	
	
	







	<tr>
		<td colspan="6" align="right">
			<span>总计：</span><span class="price_t">&yen;<span id="total">${total }</span></span>
		</td>
	</tr>
	<tr>
		<td colspan="6" bgcolor="#efeae5"><span style="font-weight: 900">收货地址</span></td>
	</tr>
	<tr>
		<td colspan="6">
			<input id="addr" type="text" name="address" value="广州市 海珠区 仲恺路500号 张三"/>
		</td>
	</tr>
	<tr>
		<td style="border-top-width: 4px;" colspan="6" align="right">
			<a id="linkSubmit" href="javascript:$('#form1').submit();">提交订单</a>
		</td>
	</tr>
</table>
</form>
  	</c:otherwise>
  </c:choose>
  </body>
</html>
