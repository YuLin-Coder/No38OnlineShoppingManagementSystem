<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>book_desc.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/storejsps/store/css/goods/desc.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>

<script type="text/javascript" src="<c:url value='/storejsps/store/js/goods/desc.js'/>"></script>

<script type="text/javascript">

$(function() {
	$("#box").attr("checked", false);
	$("#formDiv").css("display", "none");
	$("#show").css("display", "");	//默认显示信息
	
	// 操作和显示切换
	$("#box").click(function() {
		if($(this).attr("checked")) {
			$("#show").css("display", "none");
			$("#formDiv").css("display", "");//显示表单
		} else {
			$("#formDiv").css("display", "none");
			$("#show").css("display", "");//默认显示信息		
		}
	});
});

function loadChildren() {
	/*
	1. 获取pid
	2. 发出异步请求，功能之：
	  3. 得到一个数组
	  4. 获取cid元素(<select>)，把内部的<option>全部删除
	  5. 添加一个头（<option>请选择2级分类</option>）
	  6. 循环数组，把数组中每个对象转换成一个<option>添加到cid中
	*/
	// 1. 获取pid
	var pid = $("#pid").val();
	// 2. 发送异步请求
	$.ajax({
		async:true,
		cache:false,
		url:"/goods/admin/AdminBookServlet",
		data:{method:"ajaxFindChildren", pid:pid},
		type:"POST",
		dataType:"json",
		success:function(arr) {
			// 3. 得到cid，删除它的内容
			$("#cid").empty();//删除元素的子元素
			$("#cid").append($("<option>====请选择2级分类====</option>"));//4.添加头
			// 5. 循环遍历数组，把每个对象转换成<option>添加到cid中
			for(var i = 0; i < arr.length; i++) {
				var option = $("<option>").val(arr[i].cid).text(arr[i].cname);
				$("#cid").append(option);
			}
		}
	});
}

/*
 * 点击编辑按钮时执行本函数
 */
function editForm() {
	$("#method").val("edit");
	$("#form").submit();
}
/*
 * 点击删除按钮时执行本函数
 */
 function deleteForm() {
	$("#method").val("delete");
	$("#form").submit();	
}

</script>
  </head>
  
  <body>
    <input type="checkbox" id="box"><label for="box">编辑或删除</label>
    <br/>
    <br/>
  <div id="show">
    <div class="sm">${goods.gname }</div>
    <img align="top" src="<c:url value='/${goods.image_w }'/>" class="tp"/>
    <div id="book" style="float:left;">
	    <ul>
	    	<li>商品编号：${goods.gid }</li>
	    	<li>当前价：<span class="price_n">&yen;${goods.currPrice }</span></li>
	    	<li>定价：<span style="text-decoration:line-through;">&yen;${goods.price }</span>　折扣：<span style="color: #c30;">${goods.discount }</span>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
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
				<td colspan="3">所属店铺：${goods.store.sname }</td>
			</tr>
		</table>
	</div>
  </div>
  
  
  <div id='formDiv'>
   <div class="sm">&nbsp;</div>
   <form action="<c:url value='/store/StoreGoodsServlet'/>" method="post" id="form">
    <input type="hidden" name="method" id="method"/>
   	<input type="hidden" name="gid" value="${goods.gid }"/>
   	<input type="hidden" name="sid" value="${goods.store.sid }"/>
    <img align="top" src="<c:url value='/${goods.image_w }'/>" class="tp"/>
    <div style="float:left;">
	    <ul>
	    	<li>商品编号：${goods.gid }</li>
	    	<li>商品名：　<input id="gname" type="text" name="gname" value="${goods.gname }" style="width:500px;"/></li>
	    	<li>当前价：<input id="currPrice" type="text" name="currPrice" value="${goods.currPrice }" style="width:50px;"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="${goods.price }" style="width:50px;"/>
	    	折扣：<input id="discount" type="text" name="discount" value="${goods.discount }" style="width:30px;"/>折</li>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table class="tab">
			<tr>
				<td colspan="3">
					商品描述：　　<input id="description" type="text" name="description" value="${goods.description }" style="width:150px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					商品产地：　<input id="pro_area " type="text" name="pro_area " value="${goods.pro_area }" style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">所属店铺：<input id="sname" type="text" name="sname" value="${goods.store.sname }" disabled="disabled" style="width:100px;"/></td>
			</tr>
			<tr>
			<td>所属类别：<input type="text" name="pid" id="pid"
							value="${parents.cname }" disabled="disabled"
							style="width:200px;" />
						</td>
						<td>所属子类别：<select name="cid" id="cid">
								<option value="">====请选择商品子类别====</option>
								
<c:forEach items="${parents.children }" var="child" >
							<option value="${child.cid }" <c:if test="${goods.category.cid eq child.cid }">selected="selected"</c:if>>${child.cname }</option>
</c:forEach>
						</select></td>
			
				<td></td>
			</tr>
			<tr>
				<td colspan="2">
					<input onclick="editForm()" type="button" name="method" id="editBtn" class="btn" value="编　　辑">
					<input onclick="deleteForm()" type="button" name="method" id="delBtn" class="btn" value="删　　除">
				</td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
