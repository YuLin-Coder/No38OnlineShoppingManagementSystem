package cn.edu.zhku.shopping.goods.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.goods.service.GoodsService;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

/**
 * 商品模块控制层
 * @author Administrator
 *
 */
public class GoodsServlet extends BaseServlet {

	private GoodsService goodsService=new GoodsService();
	
	/**
	 * 主页菜单显示，按分类查     -----  右边商品列表显示
	 * 1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
	 * 2.得到访问资源url
	 * 3.获取分类cid，得到二级分类，进行查询
	 * 4.通过  pc（当前页）和 cid（二级分类id） 调用service同名方法进行查询
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
	    //3.获取分类cid，得到二级分类，进行查询
		String cid=req.getParameter("cid");
	   
		//4.通过  pc（当前页）和 cid（二级分类id） 调用service同名方法进行查询
		PageBean<Goods> pb=goodsService.findByCategory(cid,pc);
	  
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /jsps/goods/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		return "f:/jsps/goods/list.jsp";
	}

	/**
	 * 以商品名称进行模糊查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByGname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
	    //2.得到访问资源url
		String url=getUrl(req);
	    //3.获取商品名称：gname，得到二级分类，进行查询
		String gname=req.getParameter("gname");
	   
		//4.通过  pc（当前页）和 gname（商品名称） 调用service同名方法进行查询
		PageBean<Goods> pb=goodsService.findByGname(gname,pc);
	  
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /jsps/goods/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		return "f:/jsps/goods/list.jsp";
	}
	/**
	 * 按商品gid查询，加载商品详细信息
	 * 1.传递参数gid /GoodsServlet?method=load&bid=${goods.gid }'
	 * 2.获取相应的goods对象
	 * 3.保存到req中
	 * 4.转发到/jsps/goods/desc.jsp显示
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		 // 1.传递参数gid /GoodsServlet?method=load&gid=${goods.gid }'
		String gid = req.getParameter("gid");
		 // 2.获取相应的goods对象
		Goods goods=goodsService.load(gid);
		 // 3.保存到req中
		req.setAttribute("goods", goods);
		 // 4.转发到/jsps/goods/desc.jsp显示
		return "f:/jsps/goods/desc.jsp";
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
	 * 以商品名称进行模糊查询  ---升序查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByGnameUp(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
	    //2.得到访问资源url
		String url=getUrl(req);
	    //3.获取商品名称：gname，得到二级分类，进行查询
		String gname=req.getParameter("gname");
		
//		String gid=goodsService.findIdByGname(gname);//查找商品
	   
		//4.通过  pc（当前页）和 gname（商品名称） 调用service同名方法进行查询
		PageBean<Goods> pb=goodsService.findByGnameUp(gname,pc);
	  
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /jsps/goods/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		
//		req.setAttribute("gname", gname);//返回搜索商品名
		
		return "f:/jsps/goods/list.jsp";
	}
	
	/**
	 *  以商品名称进行模糊查询  ---降序查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByGnameDown(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.得到当前页pc （1）如果有页面传递，使用页面传递值 （2）如果没传，pc=1
		int pc=getPc(req);
	    //2.得到访问资源url
		String url=getUrl(req);
	    //3.获取商品名称：gname，得到二级分类，进行查询
		String gname=req.getParameter("gname");
		
//		String gid=goodsService.findIdByGname(gname);//查找商品
	   
		//4.通过  pc（当前页）和 gname（商品名称） 调用service同名方法进行查询
		PageBean<Goods> pb=goodsService.findByGnameDown(gname,pc);
	  
		//5.给PageBean设置url(访问资源),beanlist(当页记录) 转发到  /jsps/goods/list.jsp
		pb.setUrl(url);//此时pb对象参数才完整
		req.setAttribute("pb", pb);
		
//		req.setAttribute("gname", gname);//返回搜索商品名
		
		return "f:/jsps/goods/list.jsp";
	}
}
