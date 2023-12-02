$(function() {
	$.ajax({
		async:true,
		cache:false,
		url:"/shopping/store/StoreCategoryServlet",
		data:{method:"ajaxFindParents"},
		type:"POST",
		dataType:"json",
		success:function(arr) {
			// 得到cid，删除它的内容
			$("#cid").empty();//删除元素的子元素
			$("#cid").append($("<option>==请选择开店类型==</option>"));//添加头
			// 循环遍历数组，把每个对象转换成<option>添加到cid中
			for(var i = 0; i < arr.length; i++) {
				
				var option = $("<option>").val(arr[i].cid).text(arr[i].cname);
				$("#cid").append(option);
			}
		}
	});	
});