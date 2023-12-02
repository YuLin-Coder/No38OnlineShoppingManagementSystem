package cn.edu.zhku.shopping.order.web.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.zhku.shopping.cart.domain.CartItem;
import cn.edu.zhku.shopping.cart.service.CartItemService;
import cn.edu.zhku.shopping.order.domain.Order;
import cn.edu.zhku.shopping.order.domain.OrderItem;
import cn.edu.zhku.shopping.order.service.OrderService;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * 订单模块控制层
 * @author Administrator
 *
 */
public class OrderServlet extends BaseServlet {

	private OrderService orderService=new OrderService();
	
	private CartItemService cartItemService = new CartItemService();//调用那个购物车的方法
	
	/**
	 * 生成订单
	 * 
	 * 1.获取所有购物车条目的id，查询
	 * 2.加载所有订单条目的详细信息
	 * 3.创建List<Order> orderList
	 * 
	 * 4.创建List<store> storeList  搜索出所有店铺名字 和店铺编号
	 *   循环遍历5，6
	 * 5.创建order 
	 *       （1） 创建 属于storeList.get(i)中店铺的购物车条目，加载到order的orderItemList中
	 *       （2）往order中添加 orderItemList
	 *        (3)往orderList中添加order
	 * 6.往order中插入一个订单记录，和往 orderItem中添加多条订单条目记录
	 * 
	 * 7.批处理删除购物车记录
	 * 
	 * 8.返回orderList  到ordersuccess.jsp页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String createOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		 // 1.获取所有购物车条目的id，查询
		String cartItemIds = req.getParameter("cartItemIds");
		 // 2.加载所有订单条目的详细信息
		//订单的来源：来自于购物车的选择
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		
		if(cartItemList.size() == 0) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "您没有选择要购买的商品，不能下单！");
			return "f:/jsps/msg.jsp";
		}
		 // 3.创建List<Order> orderList
		List<Order> orderList=new ArrayList<Order>();//生成多个订单  ---循环遍历
		
		 // 4.创建List<store> storeList  搜索出所有店铺名字 和店铺编号
		List<Store> storeList=new ArrayList<Store>();//购物车中的所有店铺
		storeList=orderService.findByCartItemList(cartItemIds);
		
		 //   循环遍历5，6
		 // 5.创建order 
		 //       （1） 创建 属于storeList.get(i)中店铺的购物车条目，加载到order的orderItemList中
		 //       （2）往order中添加 orderItemList
		 //        (3)往orderList中添加order
		
//		int jk=0;
		BigDecimal allTotal = new BigDecimal("0");//全部店铺总价钱
		String format = String.format("%tF %<tT", new Date());//订单的下单时间--同步（各个店铺）
		
//		List<OrderItem> orderItemList = new ArrayList<OrderItem>();//创建订单列表
	    //开始遍历
		for (int i = 0; i < storeList.size(); i++) {
			//加入一个订单
			Order order = new Order();
			order.setOid(CommonUtils.uuid());//设置主键
			order.setOrdertime(format);//下单时间
			order.setStatus(1);//设置状态，1表示未付款
			order.setAddress(req.getParameter("address"));//设置收货地址
			User owner = (User)req.getSession().getAttribute("sessionUser");
			order.setUser(owner);//设置订单所有者
			
			
			//order.getUser().getUid(),order.getStore().getSid()存在空的问题
			Store store=new Store();//添加店铺
			store.setSid(storeList.get(i).getSid());
			//store.setSid(storeList.get(0).getSid());
			order.setStore(store);//设置店铺
			
			List<OrderItem> orderItemList = new ArrayList<OrderItem>();//创建订单列表
			
			//得到的总计
			BigDecimal total = new BigDecimal("0");
			
			for(CartItem cartItem : cartItemList) {
//				cartItem.getStore().getSid();  //订单条目sid
//              storeList.get(i);              //得到店铺sid storeList.get(0).getSid();
				
				//if(cartItem.getStore().getSid().equals(storeList.get(0).getSid()))
				
				if((cartItem.getStore().getSid()).equals( storeList.get(i).getSid() ))//属于当前店铺，添加
				{
				total = total.add(new BigDecimal(cartItem.getSubtotal() + ""));//得到总计
				allTotal = allTotal.add(new BigDecimal(cartItem.getSubtotal() + ""));//得到全部总计
				}
			}
			order.setTotal(total.doubleValue());//设置总计
			
			// 6.往order中插入一个订单记录，和往 orderItem中添加多条订单条目记录
			//创建List<OrderItem>一个CartItem对应一个OrderItem

//			List<OrderItem> orderItemList = new ArrayList<OrderItem>();//创建订单列表
			for(CartItem cartItem : cartItemList) {
				
				if(cartItem.getStore().getSid().equals(storeList.get(i).getSid()))//属于当前店铺，添加
				{
				OrderItem orderItem = new OrderItem();
				
				//item.getSubtotal(),item.getGoods().getGid(),//存在空的问题
				
				orderItem.setOrderItemId(CommonUtils.uuid());//设置主键
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setSubtotal(cartItem.getSubtotal());
				
				orderItem.setGoods(cartItem.getGoods());//商品
				//orderItem.setGoods(orderItem.getGoods());//商品
				
				orderItem.setOrder(order);//订单
				
				//orderItem.setStore(orderItem.getStore());//店铺
				orderItem.setStore(cartItem.getStore());//店铺
				
				orderItemList.add(orderItem);
				}
			}
			order.setOrderItemList(orderItemList);//  同一个店铺的一条订单 里面加载了很多订单项
		
//			orderItemList.clear();//移除订单列表里的所有元素
			
			 orderList.add(order);//加载订单
		   // 调用service完成添加
			orderService.createOrder(order);
			orderService.createOrderItem(order);//空的
			
			
		}
		StringBuilder orderOid=new StringBuilder();
		//拼接  order 的 oid字符串  进行支付
		for(int i=0;i<orderList.size();i++){
			if(i<orderList.size()-1){
			orderOid.append(orderList.get(i).getOid()+",");
			}
			else{
				orderOid.append(orderList.get(i).getOid());
			}
		}
		 // 7.批处理删除购物车记录
		cartItemService.batchDelete(cartItemIds);
		
		// 8.返回orderList  到ordersuccess.jsp页面
		req.setAttribute("orderList", orderList);//返回所有订单记录
		req.setAttribute("allTotal", allTotal);//全部总金额
		req.setAttribute("orderOid", orderOid);//返回所有订单oid,支付准备
		
		return "f:/jsps/order/ordersucc.jsp";
	}
	
	/**
	 * 我的订单
	 * 1.按uid 去查询order里的所有订单oid
	 * 2.为所有的订单加载订单项
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 从当前session中获取User
		 */
		User user = (User)req.getSession().getAttribute("sessionUser");
		
		/*
		 * 4. 使用pc和uid调用service#myOrders得到PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/order/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/order/list.jsp";
	}
		/**
		 * 获取当前页码
		 * @param req
		 * @return
		 */
		private int getPc(HttpServletRequest req) {
			int pc = 1;
			String param = req.getParameter("pc");
			if(param != null && !param.trim().isEmpty()) {
				try {
					pc = Integer.parseInt(param);
				} catch(RuntimeException e) {}
			}
			return pc;
		}
		
		/**
		 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
		 * @param req
		 * @return
		 */
		private String getUrl(HttpServletRequest req) {
			String url = req.getRequestURI() + "?" + req.getQueryString();
			/*
			 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
			 */
			int index = url.lastIndexOf("&pc=");
			if(index != -1) {
				url = url.substring(0, index);
			}
			return url;
		}
		
		
		/**
		 * 加载订单
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String load(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			String oid = req.getParameter("oid");
			Order order = orderService.load(oid);
			req.setAttribute("order", order);//订单信息
			String btn = req.getParameter("btn");//btn说明了用户点击哪个超链接来访问本方法的
			req.setAttribute("btn", btn);
			return "/jsps/order/desc.jsp";
		}
		
		/**
		 * 取消订单
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String cancel(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String oid = req.getParameter("oid");
			/*
			 * 校验订单状态
			 */
			int status = orderService.findStatus(oid);
			if(status != 1) {
				req.setAttribute("code", "error");
				req.setAttribute("msg", "状态不对，不能取消！");
				return "f:/jsps/msg.jsp";
			}
			orderService.updateStatus(oid, 5);//设置状态为取消！
			req.setAttribute("code", "success");
			req.setAttribute("msg", "您的订单已取消！");
			return "f:/jsps/msg.jsp";		
		}
		
		
		/**
		 * 确认收货
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String confirm(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String oid = req.getParameter("oid");
			/*
			 * 校验订单状态
			 */
			int status = orderService.findStatus(oid);
			if(status != 3) {
				req.setAttribute("code", "error");
				req.setAttribute("msg", "状态不对，不能确认收货！");
				return "f:/jsps/msg.jsp";
			}
			orderService.updateStatus(oid, 4);//设置状态为交易成功！
			req.setAttribute("code", "success");
			req.setAttribute("msg", "恭喜，交易成功！");
			return "f:/jsps/msg.jsp";		
		}
		
//		/**
//		 * 支付准备    --一个订单编号时
//		 * @param req
//		 * @param resp
//		 * @return
//		 * @throws ServletException
//		 * @throws IOException
//		 */
//		public String paymentPre(HttpServletRequest req, HttpServletResponse resp)
//				throws ServletException, IOException {
//			String oid=req.getParameter("oid");
//			Order order=orderService.load(oid);
//			req.setAttribute("order", order);
//			//req.setAttribute("order", orderService.load(req.getParameter("oid")));
//			
//			//return "f:/jsps/order/pay.jsp";
//			return "f:/jsps/order/ordersucc.jsp";
//		}
		
		//paymentPreAll&oid=${orderOid}
		/**
		 * 支付准备  ---多个订单编号时
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String paymentPreAll(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			String oid=req.getParameter("oid");
			String total=req.getParameter("total");//总金额
			List<Order> order=orderService.loadAll(oid);
			
			req.setAttribute("orderList", order);
			
			//req.setAttribute("orderAll", order);
			//req.setAttribute("oid", oid);//保存多个的id字符串
			req.setAttribute("allTotal", total);
			
			//orderList
			return "f:/jsps/order/ordersucc.jsp";
			
			//return "f:/jsps/order/payMore.jsp";
		}
		
		
		
		
		/**
		 * 支付
		 * 1.获取订单编号--一个订单，或者多个订单
		 * 2.修改订单状态为支付  2
		 * @param req
		 * @param resp
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 */
		public String payment(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			//1.获取订单编号--一个订单，或者多个订单
			String oid=req.getParameter("oid");//得到一个或者多个编号（编号列表）
			//2.修改订单状态为支付  2
			
			orderService.updateStatusAll(oid);//设置状态为支付成功---2！
			req.setAttribute("code", "success");
			req.setAttribute("msg", "支付成功！");
			return "f:/jsps/msg.jsp";		
		}
}
