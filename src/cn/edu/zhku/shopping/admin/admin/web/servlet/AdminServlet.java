package cn.edu.zhku.shopping.admin.admin.web.servlet;

import cn.edu.zhku.shopping.admin.admin.domain.Admin;
import cn.edu.zhku.shopping.admin.admin.service.AdminService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理模块控制层
 * 
 * @author Administrator
 * 
 */
public class AdminServlet extends BaseServlet {

	private AdminService adminService = new AdminService();

	/**
	 * 登录 1.封装表单数据到Admin 2.进行登录校验 3.返回相应页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.封装表单数据到Admin
		Admin admin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		// 2.进行登录校验
		Admin checkAdmin = adminService.login(admin);
		// 3.返回相应页面
		// 失败
		if (checkAdmin == null) {
			req.setAttribute("msg", "用户名或密码错误");
			return "/adminjsps/login.jsp";
		}
		// 成功
		req.getSession().setAttribute("admin", checkAdmin);
		return "f:/adminjsps/admin/main.jsp";
	}

	/**
	 * 退出登录
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		return "r:/jsps/adminjsps/admin/index.jsp";
	}
}
