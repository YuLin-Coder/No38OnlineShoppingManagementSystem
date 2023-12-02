package cn.edu.zhku.shopping.admin.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.admin.category.service.AdminCategoryService;
import cn.edu.zhku.shopping.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
/**
 * 管理员分类模块控制层
 * @author Administrator
 *
 */
public class AdminCategoryServlet extends BaseServlet {

	private AdminCategoryService adminCategoryService=new AdminCategoryService();
	
	/**
	 * 查询所有分类
	 * 1.查询出一级分类，及其二级分类
	 * 2.保存到request中
	 * 3.返回查询结果页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.查询出一级分类，及其二级分类
		List<Category> categoryList=adminCategoryService.findAll();
		// 2.保存到request中	
		req.setAttribute("parents", categoryList);
		// 3.返回查询结果页面
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	/**
	 * 添加一级分类
	 * 1.封装表单数据到category中
	 * 2.调用service的同名方法完成添加
	 * 3.调用findAll页面,完成更新数据后的显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.封装表单数据到category中
		Category parent = CommonUtils.toBean(req.getParameterMap(), Category.class);
		//2.调用service的同名方法完成添加
		parent.setCid(CommonUtils.uuid());//补齐数据
		adminCategoryService.addParent(parent);
		//3.调用findAll页面,完成更新数据后的显示
		
		return findAll(req,resp);
	}
	/**
	 * 添加二级分类第一步
	 * 1.获取所有相应的一级分类
	 * 2.把一级分类传递给添加页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.获取所有相应的一级分类
		String pid = req.getParameter("pid");
//		Category parent=adminCategoryService.findParent(pid);
		List<Category> parents=adminCategoryService.findParents();
		
		// 2.把一级分类传递给添加页面
		req.setAttribute("parents", parents);
		req.setAttribute("pid", pid);
		
		return "f:/adminjsps/admin/category/add2.jsp";
	}
	/**
	 * 添加二级分类第二步
	 * 1.封装表单数据到category中
	 * 2.补充数据cid
	 * 3.手动映射pid到child对象中
	 * 4.调用service方法进行添加
	 * 5.调用findAll页面,完成更新数据后的显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.封装表单数据到category中
		Category child = CommonUtils.toBean(req.getParameterMap(), Category.class);
		//2.补充数据cid
		child.setCid(CommonUtils.uuid());
		//3.手动映射pid到child对象中
		String pid = req.getParameter("pid");
		Category parent=new Category();
		parent.setCid(pid);
		child.setParent(parent);
		//4.调用service方法进行添加
		adminCategoryService.addChild(child);
		//5.调用findAll页面,完成更新数据后的显示
		return findAll(req,resp);
	}
	

	/**
	 * 修改一级分类第一步
	 *   editParentPre&cid=${parent.cid }
	 * 1.获取cid数据
	 * 2.获取一级分类的详细信息，通过cid获得该category对象
	 * 3.回显到eidt.jsp页面中
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 // 1.获取cid数据
		String cid = req.getParameter("cid");
		 // 2.获取一级分类的详细信息，通过cid获得该category对象
		Category parent=adminCategoryService.findCategory(cid);
		 // 3.回显到eidt.jsp页面中
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	/**
	 * 修改一级分类第二步
	 * 1.封装表单数据到category中
	 * 2.调用service中的edit方法完成修改
	 * 3.回显修改后的结果
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 // 1.封装表单数据到category中
		Category category=CommonUtils.toBean(req.getParameterMap(), Category.class);
		 // 2.调用service中的edit方法完成修改
		adminCategoryService.editParent(category);
		 // 3.回显修改后的结果
		return findAll(req,resp);
	}
	
	

	/**
	 * 修改二级分类第一步
	 *     editChildPre&cid=${child.cid }
	 * 1.获取cid数据
	 * 2.获取二级分类的详细信息，通过cid获得该category对象
	 * 3.查询出所有一级分类信息
	 * 4.回显到eidt2.jsp页面中
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 // 1.获取cid数据
		String cid = req.getParameter("cid");
		 // 2.获取二级分类的详细信息，通过cid获得该category对象
		Category  child=adminCategoryService.findCategory(cid);
//		Category child=CommonUtils.toBean(req.getParameterMap(),Category.class);
		 // 3.查询出所有一级分类信息
		List<Category> parents=adminCategoryService.findParents();
		 // 4.回显到eidt2.jsp页面中
		req.setAttribute("child", child);//当前二级分类信息
		req.setAttribute("parents", parents);//所有父分类
		return "f:/adminjsps/admin/category/edit2.jsp";
	}
	/**
	 * 修改二级分类第二步
	 * 1.封装表单数据到category对象
	 * 2.把表单中的pid封装到category中的parent中
	 * 3.调用service方法完成修改
	 * 4.回显修改后的结果
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.封装表单数据到category对象
		Category child=CommonUtils.toBean(req.getParameterMap(), Category.class);
		// 2.把表单中的pid封装到category中的parent中
		String pid = req.getParameter("pid");
		Category parent=new Category();
		parent.setCid(pid);
		
		child.setParent(parent);
		
		//3.调用service方法完成修改
		adminCategoryService.editChild(child);
		//4.回显修改后的结果
		return findAll(req,resp);
	}
	
	
	
	/**
	 * 删除一级分类
	 *    deleteParent&cid=${parent.cid }
	 *1.获取分类cid
	 *2.通过cid进行查询，一级分类下的二级分类个数
	 *3.如果二级分类个数等于0，则可以进行删除
	 *4.如果二级分类个数不为0，则不可以删除
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.获取分类cid
		String cid = req.getParameter("cid");
		//2.通过cid进行查询，一级分类下的二级分类个数
		long num=adminCategoryService.childNum(cid);
		//3.如果二级分类个数等于0，则可以进行删除
		if(num==0){
			adminCategoryService.deleteParent(cid);
//			return "f:/adminjsps/admin/category/list.jsp";
			return findAll(req,resp);
		}
		//4.如果二级分类个数不为0，则不可以删除
		else{
			req.setAttribute("msg", "该分类下有其它子类，不能进行删除！");
			return "f:/adminjsps/msg.jsp";
			//return "f:/adminjsps/admin/category/msg.jsp";
		}
	}
	/**
	 * 删除二级分类
	 *    deleteChild&cid=${child.cid }
	 *1.获取分类cid
	 *2.通过cid进行查询，二级分类下的商品个数
	 *3.如果商品个数等于0，则可以进行删除
	 *4.如果商品个数不为0，则不可以删除
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 //1.获取分类cid
		String cid = req.getParameter("cid");
		 //2.通过cid进行查询，二级分类下的商品个数
		long num=adminCategoryService.goodsNum(cid);
		 //3.如果商品个数等于0，则可以进行删除
		if(num==0){
			adminCategoryService.deleteChild(cid);
			return findAll(req,resp);
		}
		 //4.如果商品个数不为0，则不可以删除
		else{
			req.setAttribute("msg", "该分类下存在商品，不可以进行删除！");
			return "f:/adminjsps/msg.jsp";
		}
	}

}
