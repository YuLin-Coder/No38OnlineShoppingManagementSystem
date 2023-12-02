package cn.edu.zhku.shopping.store.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.category.service.CategoryService;
import cn.edu.zhku.shopping.store.category.service.StoreCategoryService;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.servlet.BaseServlet;
/**
 * 店铺分类模块控制层
 * @author Administrator
 *
 */
public class StoreCategoryServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StoreCategoryService storeCategoryService=new StoreCategoryService();
	
	private CategoryService categoryService=new CategoryService();//调用前台主页分类模块东西

	/**
	 * 查找所有的店铺类型，开店时用到选择店铺类型
	 * 1.通过service得到所有分类及其子分类，保存在List中
	 * 2.设置给request
	 *   
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxFindParents(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.通过service得到所有分类及其子分类，保存在List中
		List<Category> parents=categoryService.findAll();
		
		//2.设置给request
		req.setAttribute("parents", parents);
		
//		//2.设置成json格式返回
//		String json = toJson(parents);
//		resp.getWriter().print(json);
		
		return "f:/storejsps/mystore.jsp";
	}
	
//	/**
//	 * 多个category对象 写到多个json {} 中
//	 * eg:[{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa", "cname":"fdsafdas"}]
//	 * @param categoryList
//	 * @return
//	 */
//	
//	private String toJson(List<Category> categoryList) {
//		StringBuilder sb = new StringBuilder("[");
//		for(int i = 0; i < categoryList.size(); i++) {
//			sb.append(toJson(categoryList.get(i)));//一个category对象
//			if(i < categoryList.size() - 1) {
//				sb.append(",");
//			}
//		}
//		sb.append("]");
//		return sb.toString();
//	}
//	/**
//	 * 一个category对象写到一个json {} 中
//	 * eg:{"cid":"fdsafdsa", "cname":"fdsafdas"}
//	 * @param category
//	 * @return
//	 */
//	
//	private String toJson(Category category) {
//		StringBuilder sb = new StringBuilder("{");
//		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
//		sb.append(",");
//		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
//		sb.append("}");
//		return sb.toString();
//	}
	
	
	
	/**
	 * 查询所有分类，得出店铺----后台管理分类菜单
	 * 
	 * 1.获取该店铺的用户名
	 * 2.通过用户名，查询用户的店铺id（sid）
	 * 3.通过service得到该店铺的分类，保存在List中
	 * 4.设置给request
	 * 5.返回店铺的左边菜单栏
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findStoreAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		//1.获取该店铺的用户的uid
		String uid = req.getParameter("uid");
		
		//String cid = req.getParameter("cid");//获取店铺类型id
		
		//2.通过用户名，查询用户的店铺   用户的uid
		Store store=storeCategoryService.findStoreId(uid);
		
		//3.通过service得到该店铺的分类，保存在List中
		Category parents=storeCategoryService.findStoreAll(store.getSid());
		
		//4.设置给request
		req.setAttribute("parents", parents);//保存店铺分类信息
		
		req.setAttribute("store", store);//保存店铺信息
		
		//5.返回店铺的左边菜单栏
		return "f:/storejsps/store/storeCategroy/left.jsp";
	}
	
	
	
	
	
	/**
	 * 查询所有分类，得出店铺----前台管理分类菜单
	 *  以店铺名查找店铺
	 *    findStoreByName&sid=${goods.store.sid }
	 *  目的：
	 *  1.目的转发到 /storejsps/store/ownStore/main.jsp
	 *  2.得到left  中的 一级分类 和相关二级分类信息
	 *  3.得到left  店铺 对象  store  信息
	 *  4. search（框架）  中要有   store店铺名 和   搜索商品名 goods名 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findStoreByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.获取该店铺编号
		String sid= req.getParameter("sid");
		
		//String cid = req.getParameter("cid");//获取店铺类型id
		
		//2.通过店铺id，查询店铺信息
		Store store=storeCategoryService.findStoreById(sid);
		
		//3.通过service得到该店铺的分类，保存在List中
		Category parents=storeCategoryService.findStoreAll(store.getSid());
		
//		//4.设置给request
//		req.setAttribute("parents", parents);//保存店铺分类信息
//		req.setAttribute("store", store);//保存店铺信息
		
		//4.设置给session
		/**
		 * 解决了很久，因为跳转到/storejsps/store/ownStore/main.jsp，十个框架页，
		 * 里面包含4个iframe，其中包括left页面    <iframe frameborder="0"
		 *  src="<c:url value='/storejsps/store/ownStore/left.jsp '/>
		 *  
		 *  问题：
		 *  在设置attribute时，传递不了值
		 *  解决方法：
		 *  所以在要用session，使得在所有页面都可以使用！
		 */
		req.getSession().setAttribute("parents", parents);//保存店铺分类信息
		req.getSession().setAttribute("store", store);
		
		//5.返回店铺的左边菜单栏
		//return "f:/storejsps/store/ownStore/left.jsp";
		return "f:/storejsps/store/ownStore/main.jsp";
	}
//	/**
//	 * 查询所有分类，得出店铺----前台管理分类菜单
//	 * 
//	 * 1.获取该店铺的用户名
//	 * 2.通过用户名，查询用户的店铺id（sid）
//	 * 3.通过service得到该店铺的分类，保存在List中
//	 * 4.设置给request
//	 * 5.返回店铺的左边菜单栏
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	public String findStoreAllBefore(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//	
//		//1.获取该店铺的用户名
//		String loginname = req.getParameter("loginname");
//		
//		//String cid = req.getParameter("cid");//获取店铺类型id
//		
//		//2.通过用户名，查询用户的店铺id（sid）
//		Store store=storeCategoryService.findStoreId(loginname);
//		
//		//3.通过service得到该店铺的分类，保存在List中
//		Category parents=storeCategoryService.findStoreAll(store.getSid());
//		
//		//4.设置给request
//		req.setAttribute("parents", parents);//保存店铺分类信息
//		
//		req.setAttribute("store", store);//保存店铺信息
//		
//		//5.返回店铺的左边菜单栏
//		return "f:/storejsps/store/ownStore/left.jsp";
//	}
}
