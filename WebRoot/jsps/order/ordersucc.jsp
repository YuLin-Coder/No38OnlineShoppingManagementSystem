<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>ordersucc.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<!-- 		<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/pay.css'/>"> -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/ordersucc.css'/>">
  </head>
  
  <body>
  <div>
<div class="div1">
	<span class="span1">订单已生成</span>
</div>
<div class="div2">

	<c:forEach items="${orderList }" var="order">
	<table>
	<tr>
		<td>订单编号</td><td>${order.oid }</td>
   </tr>
   <tr>
		<td>合计金额</td><td>&yen;${order.total }</td>
	</tr>
	
	<tr>
	<td>收货地址</td><td>${order.address }</td>
	</tr>
	</table>
	<hr/>
	</c:forEach>
	
    <span>总合计金额：${allTotal }</span>
	<a href="<c:url value='/OrderServlet?method=payment&oid=${orderOid}&total=${allTotal  }'/>" id="linkPay">支付</a>
	<%--<a href="<c:url value='/OrderServlet?method=paymentPreAll&oid=${orderOid}&total=${allTotal  }'/>" id="linkPay">支付</a> --%>
</div>

</div>
  </body>
</html>
