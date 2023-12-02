$(function() {
	showTotal();//显示合计
	/*
	给全选添加click事件
	*/
	$("#selectAll").click(function() {
		/*
		1. 获取全选的状态
		*/
		var bool = $("#selectAll").attr("checked");
		/*
		2. 让所有条目的复选框与全选的状态同步
		*/
		setItemCheckBox(bool);
		/*
		3. 让结算按钮与全选同步
		*/
		setJieSuan(bool);
		/*
		4. 重新计算总计
		*/
		showTotal();
	});
	/*
	给所有条目的复选框添加click事件
	*/
	$(":checkbox[name=checkboxBtn]").click(function() {
		var all = $(":checkbox[name=checkboxBtn]").length;//所有条目的个数
		var select = $(":checkbox[name=checkboxBtn][checked=true]").length;//获取所有被选择条目的个数

		if(all == select) {//全都选中了
			$("#selectAll").attr("checked", true);//勾选全选复选框
			setJieSuan(true);//让结算按钮有效
		} else if(select == 0) {//谁都没有选中
			$("#selectAll").attr("checked", false);//取消全选
			setJieSuan(false);//让结算失效
		} else {
			$("#selectAll").attr("checked", false);//取消全选
			setJieSuan(true);//让结算有效				
		}
		showTotal();//重新计算总计
	});
	/*
	给减号添加click事件
	*/
	$(".jian").click(function() {
		// 获取cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		// 判断当前数量是否为1，如果为1,那就不是修改数量了，而是要删除了。
		if(quantity == 1) {
			if(confirm("您是否要删除该条目？")) {
				location = "/shopping/CartItemServlet?method=batchDelete&cartItemIds=" + id;
			}
		} else {
			sendUpdateQuantity(id, quantity-1);
		}
	});
	
	// 给加号添加click事件
	$(".jia").click(function() {
		// 获取cartItemId
		var id = $(this).attr("id").substring(0, 32);
		// 获取输入框中的数量
		var quantity = $("#" + id + "Quantity").val();
		sendUpdateQuantity(id, Number(quantity)+1);
	});
});
// 显示合计
function showTotal() {
	var total = 0;//创建total，准备累加
	/*
	1. 获取所有被勾选的复选框，遍历之
	*/
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		/*
		2. 通过复选框找到小计
		*/
		var subtotal = Number($("#" + $(this).val() + "Subtotal").text());
		total += subtotal;
	});
	/*
	3. 设置合计
	*/
	$("#total").text(round(total,2));
}
/*
 * 统一设置所有条目的复选按钮
 */
function setItemCheckBox(bool) {
	$(":checkbox[name=checkboxBtn]").attr("checked", bool);
}
/*
 * 设置结算按钮样式
 */
function setJieSuan(bool) {
	if(bool) {
		$("#jiesuan").removeClass("kill").addClass("jiesuan");
		$("#jiesuan").unbind("click");//撤消当前元素上所有click事件
	} else {
		$("#jiesuan").removeClass("jiesuan").addClass("kill");
		$("#jiesuan").click(function() {return false;});
	}
	
}
/*
 * 批量删除
 */
function batchDelete() {
	// 1. 获取所有被选中条目的复选框
	// 2. 创建一数组，把所有被选中的复选框的值添加到数组中
	// 3. 指定location为CartItemServlet，参数method=batchDelete，参数cartItemIds=数组的toString()
	var cartItemIdArray = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
	});
	location = "/shopping/CartItemServlet?method=batchDelete&cartItemIds=" + cartItemIdArray;
}

//请求服务器，修改数量。
function sendUpdateQuantity(id, quantity) {
	$.ajax({
		async:false,
		cache:false,
		url:"/shopping/CartItemServlet",
		data:{method:"updateQuantity",cartItemId:id,quantity:quantity},
		type:"POST",
		dataType:"json",
		success:function(result) {
			//1. 修改数量
			$("#" + id + "Quantity").val(result.quantity);
			//2. 修改小计
			$("#" + id + "Subtotal").text(result.subtotal);
			//3. 重新计算总计
			showTotal();
		}
	});
}
/*
 * 结算
 */
function jiesuan() {
	// 1. 获取所有被选择的条目的id，放到数组中
	var cartItemIdArray = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		cartItemIdArray.push($(this).val());//把复选框的值添加到数组中
	});	
	// 2. 把数组的值toString()，然后赋给表单的cartItemIds这个hidden
	$("#cartItemIds").val(cartItemIdArray.toString());
	// 把总计的值，也保存到表单中
	$("#hiddenTotal").val($("#total").text());
	// 3. 提交这个表单
	$("#jieSuanForm").submit();
}
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

