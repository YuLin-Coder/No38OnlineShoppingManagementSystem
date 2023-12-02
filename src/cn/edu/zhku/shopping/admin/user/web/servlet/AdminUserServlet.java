package cn.edu.zhku.shopping.admin.user.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.zhku.shopping.admin.store.service.AdminStoreService;
import cn.edu.zhku.shopping.admin.user.service.AdminUserService;
import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.edu.zhku.shopping.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
/**
 * 管理员管理用户控制层
 * @author Administrator
 *
 */
public class AdminUserServlet extends BaseServlet {

	private AdminUserService adminUserService=new AdminUserService();
	
	private AdminStoreService adminStoreService=new AdminStoreService();
	
	private UserService userService=new UserService();//借用userService里的方法
	
	/**
	 * 普通查询方法
	 * 查询所有用户
	 * 1.查询所有用户存储到list中
	 * 2.保存到requset中
	 * 3.返回到 "/adminjsps/admin/user/list.jsp"页面中显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	/*
	public String findAllUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		// 1.查询所有用户存储到list中
		List<User> userList=adminUserService.findAllUser();
		// 2.保存到requset中
		req.setAttribute("users", userList);
		// 3.返回到 "/adminjsps/admin/user/list.jsp"页面中显示
		return "f:/adminjsps/admin/user/list.jsp";
	}
	*/
	
	
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
	 * 查询所有用户
	 * 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
	 * 2.得到访问资源url
	 * 3.通过  pc（当前页）和t_user表 , 调用service同名方法进行查询
	 * 4.给PageBean设置url(访问资源),beanlist(当页记录) 转发到/adminjsps/admin/user/list.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
		 // 2.得到访问资源url
		String url=getUrl(req);
		 // 3.通过  pc（当前页）和t_user表 , 调用service同名方法进行查询
		PageBean<User> pb=adminUserService.findAllUser(pc);
		 // 4.给PageBean设置url(访问资源),beanlist(当页记录) 转发到/adminjsps/admin/user/list.jsp
	
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/user/list.jsp";
	}
	
	/**
	 * 按用户名搜索
	 *   loginname
	 * 1.获得用户名loginname
	 * 2.调用方法进行搜索，得到user
	 * 3.判断user是否为空
	 * 4.设置相应的request(userSearch)  msg 搜索情况
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String selectUserByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.获得用户名loginname
		String loginname = req.getParameter("loginname");
//		String pb = req.getParameter("pb");
		 // 2.调用方法进行搜索，得到user
		User userSearch=adminUserService.selectUserByName(loginname);
		 // 3.判断user是否为空
		//搜索失败
		if(userSearch==null){
		// 4.设置相应的request
			req.setAttribute("msg", "搜索失败,不存在该用户:"+loginname);
			req.setAttribute("userSearch", userSearch);
//			req.setAttribute("pb", pb);//分页page
			return "f:/adminjsps/admin/user/list.jsp";
		}
		else{
			req.setAttribute("msg", "搜索成功,存在用户："+loginname);
			req.setAttribute("userSearch", userSearch);
//			req.setAttribute("pb", pb);//分页page
			return "f:/adminjsps/admin/user/list.jsp";
		}
	}
	
	/**
	 * 添加用户 
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
	public String addUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1. 封装表单数据到User对象
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		//2. 校验之, 如果校验失败，保存错误信息，返回到add.jsp显示
		Map<String,String> errors = validateRegist(formUser, req.getSession());//添加用户校验
		if(errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/adminjsps/admin/user/add.jsp";
		}
		//3. 使用service完成业务
		userService.regist(formUser);
		//4. 保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "添加用户信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/user/msg.jsp";
	}
	

	/*
	 * 添加用户 校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 */
	private Map<String,String> validateRegist(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 2 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在2~20之间！");
		} else if(!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 3. 校验email
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}
		return errors;
	}

	
	/**
	 * 以用户id进行修改  第一步
	 *     editUserByIdPre&uid=${user.uid }
	 * 1.获取用户uid
	 * 2.查询用户信息
	 * 3.回显用户信息，在edit页面回显/adminjsps/admin/user/edit.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUserByIdPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 // 1.获取用户uid
		String uid = req.getParameter("uid");
		 // 2.查询用户信息
		User user=adminUserService.findUser(uid);
		
		 // 3.回显用户信息，在edit页面回显/adminjsps/admin/user/edit.jsp
		req.setAttribute("form", user);
		return "f:/adminjsps/admin/user/edit.jsp";
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
		//1. 封装表单数据到User对象
		String uid=req.getParameter("uuid");//获取要修改的id
		
		String befoLoginname=req.getParameter("befoLoginname");//以前的登录名
		
		String befoEmail=req.getParameter("befoEmail");//以前的邮箱
		
		
		//2.获取表单数据
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		Map<String,Object> errors=new HashMap<String,Object>();
		//3.思路：如果用户名和邮箱是以前的，则不用验证  用户名和邮箱是否已经存在
		if(!befoLoginname.equals(formUser.getLoginname())){
			int value=adminUserService.findByLoginname(formUser);//判断是否存在
			if(value==1){
			errors.put("loginname", "用户名已经存在，请重新输入！");
			formUser.setUid(uid);
			req.setAttribute("errors", errors);
			req.setAttribute("form", formUser);
			return "f:/adminjsps/admin/user/edit.jsp";
			}
		}
		//4.用户名和邮箱都不是现在的，提示重新输入
		if(!befoEmail.equals(formUser.getEmail())){
			int value=adminUserService.findByEmail(formUser);//判断是否存在
			if(value==1){
			errors.put("email", "用户名已经存在，请重新输入！");
			
			formUser.setUid(uid);
			req.setAttribute("errors", errors);
			req.setAttribute("form", formUser);
			return "f:/adminjsps/admin/user/edit.jsp";
			}
		}
		
		
		//5. 使用service完成业务
		adminUserService.editUserById(formUser,uid);
		//6. 保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "修改用户信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/user/msg.jsp";
	}
	/**
	 * 以用户id进行删除
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
	public String deleteUserById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.获取用户uid
		String uid = req.getParameter("uid");
		 // 2.删除该用户
		adminUserService.deleteUserById(uid);
		 // 3.保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "删除用户信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/user/msg.jsp";
	}
	
	
	
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
		String uid=req.getParameter("uid");
		
		List<Category> parents=new ArrayList<Category>();
		parents=adminStoreService.findCategory();//查询所有的一级分类
//		req.setAttribute("parents", parents);//保存一级分类信息
		req.getSession().setAttribute("parents", parents);
		req.setAttribute("uid", uid);
		return "f:/adminjsps/admin/user/addStore.jsp";
		
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
		
		String uid=req.getParameter("uid");
		User user=new User();
		user.setUid(uid);
		
		String sname=req.getParameter("sname");
		Store store=new Store();
		store.setSname(sname);
		
		String cid=req.getParameter("cid");
		Category category=new Category();
		category.setCid(cid);
		
		store.setCategory(category);
		store.setUser(user);
		
		//2. 校验之, 如果校验失败，保存错误信息，返回到add.jsp显示
		Map<String,String> errors = validateAddStore(store);//添加店铺校验
		
		if(errors.size() > 0) {
			req.setAttribute("store", store);
			req.setAttribute("errors", errors);
			return "f:/adminjsps/admin/user/addStore.jsp";
		}
		//3. 使用service完成业务
		store.setSid(CommonUtils.uuid());
		
		adminUserService.addStore(store);
		
		//4. 保存成功信息，转发到msg.jsp显示
		req.setAttribute("msg", "添加店铺信息成功！");
		req.setAttribute("code", "success");
		return "f:/adminjsps/admin/user/msg.jsp";
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
	
		//2.店铺类型为空，不通过
		String cid=store.getCategory().getCid();
		if(cid== null || cid.trim().isEmpty()) {
			errors.put("cid", "类型不能为空！");
		} 
		return errors;
	}
	
	
//	/**
//	 * 以店铺sid进行删除
//	 *   deleteUserById&uid=${user.uid }
//	 * 1.获取用户uid
//	 * 2.删除该用户
//	 * 3.保存成功信息，转发到msg.jsp显示
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	public String deleteStoreById(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		
//		 // 1.获取获取用户id
//		String uid = req.getParameter("uid");
//		
//		//2。查找用户的店铺sid
//		String sid=adminUserService.findStore(uid);
//		 //3.删除该用户的店铺  ，以及设置修改用户开店状态为0
//		adminStoreService.deleteUserById(sid);
//		 // 3.保存成功信息，转发到msg.jsp显示
//		req.setAttribute("msg", "封店状态成功！");
//		req.setAttribute("code", "success");
//		return "f:/adminjsps/admin/user/msg.jsp";
//	}
}


