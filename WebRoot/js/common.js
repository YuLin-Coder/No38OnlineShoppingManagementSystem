function _change() {
	$("#vCode").attr("src", "/shopping/VerifyCodeServlet?" + new Date().getTime());
}