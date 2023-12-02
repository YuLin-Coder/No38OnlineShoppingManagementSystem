package cn.edu.zhku.shopping.admin.store.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.admin.store.service.AdminStoreService;
import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
/**
 *管理员管理店铺控制层
 * @author Administrator
 *
 */
public class AdminStoreServlet extends BaseServlet {

	private AdminStoreService adminStoreService=new AdminStoreService();
	
	/**
	 * 截取url，使得分页的除页码外的链接资源相同，保证访问的是同一个资源
	 *    （前缀相同，后缀不同，&pc=?不同）
	 * 1.url 不存在  &pc  使用默认url 
	 * 2.url 存在  &pc  ,截取掉&pc 
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req) {
		
		 //1.url 不存在  &pc  使用默认url 
		//shopping/GoodsServlet?method=findByCategory&cid=xxx&pc=3
		String url=req.getRequestURI()+"?"+req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		 //2.url 存在  &pc  ,截取掉&pc 
		if(index!=-1){
			url=url.substring(0, index);
		}
		return url;
	}

	/**
	 * 获取当前页面
	 * 1.当前页为空，默认为1（第一页）
	 * 2.当前页面不为空时，为pc
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		//当前页为空，默认为1（第一页）
		int pc=1;
		String param=req.getParameter("pc");
		//当前页面不为空时，为pc
		if(param!=null &&!param.trim().isEmpty()){
			try{
			pc=Integer.parseInt(param);
			}catch(RuntimeException e){}
		}
		return pc;
		}
	/**
	 * 分页查询
	 * 查询所有店铺
	 * 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
	 * 2.得到访问资源url
	 * 3.通过  pc（当前页）和t_store表 , 调用service同名方法进行查询
	 * 4.给PageBean设置url(访问资源),beanlist(当页记录) 转发到/adminjsps/admin/store/list.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllStore(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
		 // 2.得到访问资源url
		String url=getUrl(req);
		 // 3.通过  pc（当前页）和t_Store表 , 调用service同名方法进行查询
		PageBean<Store> pb=adminStoreService.findAllStore(pc);
		 // 4.给PageBean设置url(访问资源),beanlist(当页记录) 转发到/adminjsps/admin/Store/list.jsp
	
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		
		return "f:/adminjsps/admin/store/list.jsp";
	}
	
	/**
	 * 按店铺名搜索
	 *   sname
	 * 1.获得用户名sname
	 * 2.调用方法进行搜索，得到store
	 * 3.判断store是否为空
	 * 4.设置相应的request(storeSearch)  msg 搜索情况
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String selectStoreByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.获得用户名loginname
		String sname = req.getParameter("sname");
//		String pb = req.getParameter("pb");
		 // 2.调用方法进行搜索，得到user
		Store storeSearch=adminStoreService.selectStoreByName(sname);
		 // 3.判断user是否为空
		//搜索失败
		if(storeSearch==null){
		// 4.设置相应的request
			req.setAttribute("msg", "搜索失败,不存在该店铺:"+sname);
			req.setAttribute("storeSearch", storeSearch);
//			req.setAttribute("pb", pb);//分页page
			return "f:/adminjsps/admin/user/list.jsp";
		}
		else{
			req.setAttribute("msg", "搜索成功,存在店铺："+sname);
			req.setAttribute("storeSearch", storeSearch);
//			req.setAttribute("pb", pb);//分页page
			return "f:/adminjsps/admin/store/list.jsp";
		}
	}
	
	//addStorePre
	/**
	 * 添加店铺准备
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addStorePre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Category> parents=new ArrayList<Category>();
		parents=adminStoreService.findCategory();//查询所有的一级分类
//		req.setAttribute("parents", parents);//保存一级分类信息
		req.getSession().setAttribute("parents", parents);
		return "f:/adminjsps/admin/store/add.jsp";
		
	}
	/**
	 * 添加店铺
	 * 1.封装表单数据到User对象
	 * 2. 校验之, 如果校验失败，保存错误信息，返回到add.jsp显示
	 * 3. 使用service完成业务
	 * 4. 保存成功信息，转发到msg.jsp显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addStore(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1. 封装表单数据到store对象
		String sname=req.getParameter("sname");
		Store store=new Store();
		store.setSname(sname);
//		Store store = CommonUtils.toBean(req.getParameterMap(), Store.class);
		
		String loginname=req.getParameter("loginname");
		User user = new User();
		user.setLoginname(loginname);
		
		String cid=req.getParameter("cid");
		Category category=new Category();
		category.setCid(cid);
		
//		Category category=CommonUtils.toBean(req.getParameterMap(), Category.class);
		
		store.setUser(user);
		store.setCategory(category);
		
		//2. 校验之, 如果校验失败，保存错误信息，返回到add.jsp显示
		Map<String,String> errors = validateAddStore(store);//添加店铺校验
		
		if(errors.size() > 0) {
			req.setAttribute("store", store);
			req.setAttribute("errors", errors);
			return "f:/adminjsps/admin/store/add.jsp";
		}
		//3. 使用service完成业务
		store.setSid(CommonUtils.uuid());
		
		adminStoreService.addStore(store);
		
		//4. 保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "添加店铺信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/store/msg.jsp";
	}
	
	/*
	 * 添加店铺校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 */
	private Map<String,String> validateAddStore(Store store) {
		Map<String,String> errors = new HashMap<String,String>();
		
		//1.店铺名不同名校验，同名则显示错误
		String sname=store.getSname();
		if(sname == null || sname.trim().isEmpty()) {
			errors.put("sname", "店铺名不能为空！");
		} else if(sname.length() < 2 || sname.length() > 20) {
			errors.put("sname", "店铺名长度必须在2~20之间！");
		} else if(!adminStoreService.ajaxValidateSname(sname)) {
			errors.put("sname", "店铺名已存在！");
		}
		
		//2.所属用户名是否存在，如果不存在，返回false
		String loginname=store.getUser().getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(adminStoreService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "不存在该用户");
		}
		
		//3.店铺类型为空，不通过
		String cid=store.getCategory().getCid();
		if(cid== null || cid.trim().isEmpty()) {
			errors.put("cid", "类型不能为空！");
		} 
		return errors;
	}
	
	/**
	 * 修改店铺信息第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editStoreByIdPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 // 1.获取店铺的sid
		String sid = req.getParameter("sid");
		 // 2.查询店铺的店铺信息和用户信息
		Store store=adminStoreService.findStore(sid);
		
		//3.查找所有一级分类
		List<Category> parents=adminStoreService.findCategory();//查询所有的一级分类
		req.getSession().setAttribute("parents", parents);
		
		 // 3.回显用户信息，在edit页面回显/adminjsps/admin/user/edit.jsp
		req.setAttribute("form", store);
		req.getSession().setAttribute("store", store);
		return "f:/adminjsps/admin/store/edit.jsp";
	}
	
	/**
	 *  以用户id进行修改 第二步
	 * 1. 封装表单数据到User对象
	 * 2. 使用service完成业务
	 * 3. 保存成功信息，转发到msg.jsp显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUserById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	
		
		//1.获取sname  cid
		String BeforeName=req.getParameter("BeforeName");//旧的店铺名
		
		//2. 得到sid
		String sid=req.getParameter("sid");
		//String sid=adminStoreService.findStoreByname(BeforeName);
		
		String sname=req.getParameter("sname");//新的店铺名
		String cid=req.getParameter("cid");//类型
		
		Store store=new Store();
		Category category=new Category();
		
		category.setCid(cid);
		
		store.setCategory(category);
		store.setSname(sname);
		
		//3.修改店铺信息店铺校验
		Map<String,String> errors = new HashMap<String,String>();
		//1.店铺名不同名校验，同名则显示错误
		if(sname == null || sname.trim().isEmpty()) {
			errors.put("sname", "店铺名不能为空！");
		}
		//2.校验长度
		else if(sname.length() < 2 || sname.length() > 20) {
			errors.put("sname", "店铺名长度必须在2~20之间！");
		}
		 //名字修改后，校验是否存在同名店铺
		//3.校验店铺名是否改变，1.如果改变，检验是否存在2.如果没有改变，则不用校验
		else if(!BeforeName.equals(sname)){
			if(!adminStoreService.ajaxValidateSname(sname))
			{
				errors.put("sname", "店铺名已存在！");
			}
		} 
			
			
		if(errors.size() > 0) {
			req.setAttribute("form", store);
			req.setAttribute("errors", errors);
			return "f:/adminjsps/admin/store/edit.jsp";
		}
		//4. 使用service完成业务
		adminStoreService.editStoreById(sname,cid,sid);
		
		//5. 保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "修改店铺户信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/store/msg.jsp";
	}

	/**
	 * 以店铺sid进行删除
	 *   deleteUserById&uid=${user.uid }
	 * 1.获取用户uid
	 * 2.删除该用户
	 * 3.保存成功信息，转发到msg.jsp显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteStoreById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.获取店铺sid
		String sid = req.getParameter("sid");
		 // 2.删除该用户
		adminStoreService.deleteUserById(sid);
		 // 3.保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "删除用户信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/user/msg.jsp";
	}
}
