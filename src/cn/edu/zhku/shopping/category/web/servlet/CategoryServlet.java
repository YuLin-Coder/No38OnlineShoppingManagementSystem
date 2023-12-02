package cn.edu.zhku.shopping.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
/**
 * 分类模块控制层
 * @author Administrator
 *
 */
public class CategoryServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private CategoryService categoryService=new CategoryService();
	
	/**
	 * 查询所有分类，得出主页分类菜单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1.通过service得到所有分类及其子分类，保存在List中
		 * 2.设置给requset
		 * 3.返回主页的左边菜单栏
		 */
		
		//1.通过service得到所有分类及其子分类，保存在List中
		List<Category> parents=categoryService.findAll();
		//2.设置给request
		req.setAttribute("parents", parents);
		//3.返回主页的左边菜单栏
		return "f:/jsps/left.jsp";
	}
}
