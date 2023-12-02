<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>产品列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/goods/list.css'/>">
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
    
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='/jsps/js/goods/list.js'/>"></script>
  </head>
  
  <body>

<ul>
<c:forEach items="${pb.beanList }" var="goods">
  <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/store/StoreGoodsServlet?method=load&gid=${goods.gid }'/>"><img src="<c:url value='/${goods.image_b }'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">&yen;${goods.currPrice }</span>
		<span class="price_r">&yen;${goods.price }</span>
		(<span class="price_s">${goods.discount }折</span>)
	</p>
	<p><a id="goodsname" title="${goods.gname }" href="<c:url value='/store/StoreGoodsServlet?method=load&gid=${goods.gid }'/>">${goods.gname }</a></p>
	
		<p>商品描述： ${goods.description }</p>
		<p>商品产地： ${goods.pro_area }</p>
	    <p>店铺：${goods.store.sname }</p> 
	 

	<%-- url标签会自动对参数进行url编码 --%>
<%--

<!-- 	<c:url value="/goodsServlet" var="authorUrl"> -->
<!-- 		<c:param name="method" value="findByAuthor"/> -->
<!-- 		<c:param name="author" value="${goods.author }"/> -->
<!-- 	</c:url> -->
<!-- 	<p><a href="${authorUrl }" name='P_zz' title='${goods.author }'>${goods.author }</a></p> -->

<!-- 	<c:url value="/GoodsServlet" var="store"> -->
<!-- 		<c:param name="method" value="findByStore"/> -->
<!-- 		<c:param name="sname" value="${goods.store.name }"/> -->
<!-- 	</c:url> -->
<!-- 	<p><a href="${store }" name='P_zz' title='${goods.store.sname }'>${goods.store.sname }</a></p> -->
 --%>
  </div>
  </li>
</c:forEach>
</ul>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>

  </body>
 
</html>

