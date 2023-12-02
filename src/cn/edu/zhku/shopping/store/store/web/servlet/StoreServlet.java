package cn.edu.zhku.shopping.store.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.store.store.service.StoreService;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class StoreServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StoreService storeService=new StoreService();
	/**
	 * 用户开店
	 * 1.获取开店信息
	 * 2.利用service同名方法进行开店
	 * 3.返回开店后的页面
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String creatStore(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.获取开店信息
		Store store = CommonUtils.toBean(req.getParameterMap(), Store.class);

		String cid = req.getParameter("cid");
		String uid=req.getParameter("uid");
		//2.利用service同名方法进行开店
		storeService.creatStore(store,cid,uid);
		//3.返回开店后的页面
		
		/**
		 * 写法1：
		 * 框架页的跳转问题：/storejsps/store/index.jsp  --> /storejsps/store/main.jsp
		 * 从里层框架跳转到外层框架
		 * 在index.jsp写代码如下
		 * <script>
	     * window.parent.parent.location.href='<%=basePath%>/storejsps/store/main.jsp';
	     * </script>
		 */
		
		/**
		 * 写法2：在 html target="body"  ---> target="_parent" 
		 */
		
		//删除原来session对象
		User user = (User) req.getSession().getAttribute("sessionUser");
		req.getSession().removeAttribute("sessionUser");
		
		User user2=new User();
		user2.setLoginname(user.getLoginname());
		user2.setLoginpass(user.getLoginpass());
		user2.setUid(user.getUid());
		user2.setEmail(user.getEmail());
		user2.setIsStore(1);
		//创建新的session对象---保存
		req.getSession().setAttribute("sessionUser", user2);
		
		req.setAttribute("store",store );
		
		return "f:/storejsps/store/main.jsp";
//		return "f:/storejsps/store/index.jsp";
	}

}
