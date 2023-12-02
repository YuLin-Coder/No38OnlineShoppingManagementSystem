<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>左侧导航</title>
<head>

<style type="text/css">
body{margin:0;padding:0;overflow-x:hidden;}
html, body{height:100%;}
img{border:none;}
*{font-family:'微软雅黑';font-size:12px;color:#626262;}
dl,dt,dd{display:block;margin:0;}
a{text-decoration:none;}

#bg{background-image:url(images/content/dotted.png);}
.container{width:100%;height:100%;margin:auto;}

/*left*/
.leftsidebar_box{width:160px;height:auto !important;overflow:visible !important;position:fixed;height:100% !important;background-color:#DD2727;}
.line{height:2px;width:100%;background-image:url(images/left/line_bg.png);background-repeat:repeat-x;}
.leftsidebar_box dt{padding-left:40px;padding-right:10px;background-repeat:no-repeat;background-position:10px center;color:#f5f5f5;font-size:14px;position:relative;line-height:48px;cursor:pointer;}
.leftsidebar_box dd{background-color:#FFA54F;padding-left:40px;}
.leftsidebar_box dd a{color:#f5f5f5;line-height:20px;}
.leftsidebar_box dt img{position:absolute;right:10px;top:20px;}
.system_log dt{background-image:url(images/left/system.png)}
.custom dt{background-image:url(images/left/custom.png)}
.channel dt{background-image:url(images/left/channel.png)}
.app dt{background-image:url(images/left/app.png)}
.cloud dt{background-image:url(images/left/cloud.png)}
.syetem_management dt{background-image:url(images/left/syetem_management.png)}
.source dt{background-image:url(images/left/source.png)}
.statistics dt{background-image:url(images/left/statistics.png)}
.leftsidebar_box dl dd:last-child{padding-bottom:10px;}
</style>

</head>

<body id="bg">

<div class="container">

	<div class="leftsidebar_box">
		<div class="line"></div>
		<dl class="system_log">
			<dt onClick="changeImage()">个人中心</dt>
			<dd class="first_dd"><a href="<c:url value='/jsps/userImfo/personInfo.jsp'/>" target="body">个人信息</a></dd>
		</dl>
	
		<dl class="custom">
			<dt onClick="changeImage()">我的订单</dt>
			<dd class="first_dd"><a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">订单管理</a></dd>

		</dl>
	
		<dl class="channel">
			<dt>我的购物车</dt>
			<dd class="first_dd"><a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">购物车管理</a></dd>
		</dl>
	
		<dl class="app">
			<dt onClick="changeImage()">我的密码</dt>

			<dd class="first_dd"><a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a></dd>
		</dl>
		<%-- 
		sessionUser
		<c:if test="${requestScope.sessionUser.isStore!=0} ">
		<dl class="app">
			<dt onClick="changeImage()">我的店铺</dt>
			<dd class="first_dd"><a href="#">店铺管理</a></dd>
		</dl>
		</c:if>
		
		--%> 
	</div>

</div>

<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/main.css'/>">

<script type="text/javascript" src="<c:url value='/jsps/js/userImfo/jquery.js'/>"></script>
<script type="text/javascript">
$(".leftsidebar_box dt").css({"background-color":"#DD2727"});
$(function(){
	$(".leftsidebar_box dd").hide();
	$(".leftsidebar_box dt").click(function(){
		$(".leftsidebar_box dt").css({"background-color":"#DD2727"})
		$(this).css({"background-color": "#DD2727"});
		$(this).parent().find('dd').removeClass("menu_chioce");
		$(".menu_chioce").slideUp(); 
		$(this).parent().find('dd').slideToggle();
		$(this).parent().find('dd').addClass("menu_chioce");
	});
})
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>


			


