<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>按名称查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/button.css'/>" />
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
		
	<script type="text/javascript">
	function searchAction(){
	   var x=$("#gname").val();
	    if (!x) {
			alert("没输入搜索名搜索，请输入！");
		return false;
		}
		
	    document.demo.action="/shopping/GoodsServlet?method=findByGname&gname="+x;
	    document.demo.submit();
	    return true;
	    }
	function searchUpAction(){
	   var x=$("#gname").val();
	    if (!x) {
			alert("没输入搜索名搜索，不能排序！");
		return false;
		}
	    document.demo.action="/shopping/GoodsServlet?method=findByGnameUp&gname="+x;
	    document.demo.submit();
	    return true;
	}
	function searchDownAction(){
	   var x=$("#gname").val();
	    if (!x) {
			alert("没输入搜索名搜索，不能排序！");
		return false;
		}
	    document.demo.action="/shopping/GoodsServlet?method=findByGnameDown&gname="+x;
	    document.demo.submit();
	    return true;
	}
</script>
</head>
  
  <body>
  <div align="center">
 <form name="demo" id="search" method="post" target="body">
 &nbsp;&nbsp;&nbsp;
<input type="text" name="gname"  id="gname"  class="gname" style="height: 30px;"/>
<input type="button" class="button red"  id="search1" value="搜索" onclick="searchAction()">
&nbsp;&nbsp;&nbsp;
<input type="button"  class="button red" id="search2" value="按价格升序搜索" onclick="searchUpAction()">
<input type="button" class="button red"  id="search3" value="按价格降序搜索" onclick="searchDownAction()">
</form>
</div>
  <%-- 
  <table>
  <tr>
  <td width=200px></td>
  <td align="left">
    <form action="<c:url value='/GoodsServlet'/>" method="get" target="body" id="form1">
    	<input type="hidden" name="method" value="findByGname"/>
    	<input type="text" name="gname"  id="gname"  class="gname" style="height:50px;width: 300px"/>
    	<span>
    		<a href="javascript:document.getElementById('form1').submit();"><img align="top" border="0" src="../images/btn_search.jpg"/></a>
    	</span>
    </form>
    </td>
    
    <td align="right">
     <form action="<c:url value='/GoodsServlet'/>" method="post" target="body" id="form2">
    	<input type="hidden" name="method" value="findByGnameUp"/>
    	<input type="hidden" name="gname" value="${sessionScope.gname }" />
    	<input type="submit" value="按价格升序" id="first" class="first" />
    </form>
    </td>
     <td align="right">
     <form action="<c:url value='/GoodsServlet'/>" method="post" target="body" id="form3">
    	<input type="hidden" name="method" value="findByGnameDown"/>
    	<input type="hidden" name="gname" value="${sessionScope.gname }" />
    	<input type="submit" value="按价格降序" id="two" class="two"/>
    </form>
    </td>
    </table>
    
    --%>
  </body>
</html>
