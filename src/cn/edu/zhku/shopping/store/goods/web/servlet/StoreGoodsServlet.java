package cn.edu.zhku.shopping.store.goods.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.category.service.StoreCategoryService;
import cn.edu.zhku.shopping.store.goods.service.StoreGoodsService;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * 店铺管理商品 控制层
 * @author Administrator
 *
 */
public class StoreGoodsServlet extends BaseServlet {
 
	
	private StoreGoodsService storeGoodsService=new StoreGoodsService();
	
	//调用的店铺分类方法，得到店铺一级分类和二级分类
	private StoreCategoryService storeCategoryService=new StoreCategoryService();
	

	/**
	 * 查询店铺的分类下的全部商品  --通过二级分类 cid,sid  ------前台
	 *     findByCategory&cid=${child.cid}&sid=${sid}
	 *     
	 * 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
	 * 2.得到访问资源url
	 * 3.获取分类cid，店铺sid，得到二级分类，和所属店铺，进行查询
	 * 4.通过  pc（当前页）, cid（二级分类id）,店铺sid 调用service同名方法进行查询
	 * 5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /jsps/goods/list.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
	    //2.得到访问资源url
		String url=getUrl(req);
		
		//3.获取分类cid，店铺sid，得到二级分类，和所属店铺，进行查询

		String cid = req.getParameter("cid");//获取二级分类cid

		String sid = req.getParameter("sid");//获取店铺sid
		
		//4.通过  pc（当前页）, cid（二级分类id）,店铺sid 调用service同名方法进行查询
		PageBean<Goods> pb=storeGoodsService.findByCategory(cid,sid,pc);
	  
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /storejsps/store/storeCategroy/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		return "f:/storejsps/store/storeCategroy/list.jsp";
	}
	
	/**
	 * 查询店铺的分类下的全部商品  --通过二级分类 cid,sid   ----后台
	 *     findByCategory&cid=${child.cid}&sid=${sid}
	 *     
	 * 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
	 * 2.得到访问资源url
	 * 3.获取分类cid，店铺sid，得到二级分类，和所属店铺，进行查询
	 * 4.通过  pc（当前页）, cid（二级分类id）,店铺sid 调用service同名方法进行查询
	 * 5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /jsps/goods/list.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategoryAfter(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
	    //2.得到访问资源url
		String url=getUrl(req);
		
		//3.获取分类cid，店铺sid，得到二级分类，和所属店铺，进行查询

		String cid = req.getParameter("cid");//获取二级分类cid

		String sid = req.getParameter("sid");//获取店铺sid
		
		//4.通过  pc（当前页）, cid（二级分类id）,店铺sid 调用service同名方法进行查询
		PageBean<Goods> pb=storeGoodsService.findByCategory(cid,sid,pc);
	  
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /storejsps/store/storeCategroy/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		return "f:/storejsps/store/ownStore/list.jsp";
	}


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
	 * 以商品名称和店铺的用户id进行模糊查询
	 * 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
	 * 2.得到访问资源url
	 * 3.获取店铺的用户sid，和商品名称gname
	 * 4.通过  pc（当前页）和 uid,gname 调用service同名方法进行查询
	 * 5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /storejsps/store/storeCategroy/list.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String StorefindByGnameAndUid(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
		//2.得到访问资源url
		String url=getUrl(req);
		//3.获取店铺的用户uid，和商品名称gname
		String uid = req.getParameter("uid");
		String gname=req.getParameter("gname");
		
		//4.用uid查询用户对象
		Store store=storeGoodsService.StorefindByUid(uid);
		
	    //4.通过  pc（当前页）和 sid,gname 调用service同名方法进行查询
		PageBean<Goods> pb=storeGoodsService.StorefindByGnameAndUid(store.getSid(),gname,pc);
		
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /storejsps/store/storeCategroy/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		return "f:/storejsps/store/storeCategroy/list.jsp";
	}
	

	
	
	/**
	 * 添加商品---第一步
	 *    addPre&uid=${sessionScope.sessionUser.uid }
	 * 目的：获取店铺的分类信息
	 *   转发到：/storejsps/store/storeCategroy/add.jsp
	 * 1.获得一级分类  和 二级分类 parents
	 * 2.获得店铺信息 store
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.获取店铺的用户uid
		String uid = req.getParameter("uid");
		Store store=storeGoodsService.StorefindByUid(uid);
		
		//2.调用的店铺分类方法，得到店铺一级分类和二级分类
		//调用storeCategoryService
		Category parents=storeCategoryService.findStoreAll(store.getSid());
		
		//3.设置给request
		req.getSession().setAttribute("parents", parents);//保存店铺分类信息
		//req.setAttribute("parents", parents);//保存店铺分类信息
		req.setAttribute("store", store);//保存店铺信息
		return "f:/storejsps/store/storeCategroy/add.jsp";
	}
	
	/**
	 * 加载商品详细信息------------前台
	 *    method=load&gid=${goods.gid }
	 * 1.获得category 一级分类和二级分类
	 * 2.获得store   店铺名
	 * 3.获得 goods   商品信息
	 * 4.转发到： /storejsps/store/storeCategroy/desc.jsp
	 *    1.该页面可以进行查看商品详细信息和店铺信息
	 *    2.点击编辑活删除  还可以看到分类信息
	 *    
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.获得gid
		String gid = req.getParameter("gid");
		
		// 2.获取相应的goods对象
		Goods goods=storeGoodsService.load(gid);
		
		//3.获得cateogry对象  一级分类pid
		Category parents=storeGoodsService.loadCategory(goods.getCategory().getCid());
	    // 4.保存到req中
		req.setAttribute("goods", goods);
		req.setAttribute("parents", parents);
		
	   // 5.转发到/jsps/goods/desc.jsp显示
		return "f:/storejsps/store/storeCategroy/desc.jsp";
	}
	
	/**
	 * 加载商品详细信息  ----------后台
	 *    method=load&gid=${goods.gid }
	 * 1.获得category 一级分类和二级分类
	 * 2.获得store   店铺名
	 * 3.获得 goods   商品信息
	 * 4.转发到： /storejsps/store/storeCategroy/desc.jsp
	 *    1.该页面可以进行查看商品详细信息和店铺信息
	 *    2.点击编辑活删除  还可以看到分类信息
	 *    
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loadAfter(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.获得gid
		String gid = req.getParameter("gid");
		
		// 2.获取相应的goods对象
		Goods goods=storeGoodsService.load(gid);
		
		//3.获得cateogry对象  一级分类pid
		Category parents=storeGoodsService.loadCategory(goods.getCategory().getCid());
	    // 4.保存到req中
		req.setAttribute("goods", goods);
		req.setAttribute("parents", parents);
		
	   // 5.转发到/jsps/goods/desc.jsp显示
		return "f:/storejsps/store/ownStore/desc.jsp";
	}
	
	/**
	 * 修改商品信息
	 * 1.封装表单数据到goods对象中
	 * 2.封装cid到Category中
	 * 3.把sid封装到store中
	 * 4.把Category,store复制给goods
	 * 5.调用service完成工作
	 * 6.保存成功信息，转发到msg.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 //1.封装表单数据到goods对象中
		Map<String, String[]> map = req.getParameterMap();
		Goods goods = CommonUtils.toBean(map, Goods.class);
		 // 2.封装cid到Category中
		Category category = CommonUtils.toBean(map, Category.class);
		 // 3.把sid封装到store中
		Store store = CommonUtils.toBean(map, Store.class);
		 // 4.把Category,store复制给goods
		goods.setCategory(category);
		goods.setStore(store);
		 // 5.调用service完成工作
		storeGoodsService.edit(goods);
		
		 // 6.保存成功信息，转发到msg.jsp
		req.setAttribute("msg", "修改商品信息成功！");
		return "f:/storejsps/store/storeCategroy/msg.jsp";
	}
	
	/**
	 * 删除商品信息
	 * 1.获取商品gid
	 * 2.删除商品包含的两张大图和小图文件
	 * 3.删除商品
	 * 4.保存成功信息，转发到msg.jsp
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.获取商品gid
		String gid = req.getParameter("gid");
		Goods goods = storeGoodsService.load(gid);
		
		 // 2.删除商品包含的两张大图和小图文件
		String savepath=this.getServletContext().getRealPath("/");//获取真实路径
		new File(savepath,goods.getImage_w()).delete();//删除大图
		new File(savepath,goods.getImage_b()).delete();//删除小图
		// 3.删除商品
		storeGoodsService.delete(gid);
		// 4.保存成功信息，转发到msg.jsp
		req.setAttribute("msg", "删除商品成功！");
		return "f:/storejsps/store/storeCategroy/msg.jsp";
	}
	
}
