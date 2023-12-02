package cn.edu.zhku.shopping.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.edu.zhku.shopping.order.dao.OrderDao;
import cn.edu.zhku.shopping.order.domain.Order;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.jdbc.JdbcUtils;

/**
 * 订单模块业务层
 * @author Administrator
 *
 */
public class OrderService {

	private OrderDao orderDao=new OrderDao();

	/**
	 * 获得购物车中 所有店铺名字 和店铺编号
	 * @param cartItemList
	 * @return
	 */
	public List<Store> findByCartItemList(String cartItemIds) {
		try {
			return orderDao.findByCartItemList(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成订单
	 * @param order
	 */
	public void createOrder(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);//存在空的问题
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成订单条目
	 * @param order
	 */
	public void createOrderItem(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.addOrderItem(order);//存在空的问题
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> myOrders(String uid, int pc) {
		try {
			JdbcUtils.beginTransaction();//开启事务
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();//提交事务
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();//回滚事务
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 加载订单---一个订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载订单---多个订单
	 * @param oid
	 * @return
	 */
	public List<Order> loadAll(String oid) {
		try {
			JdbcUtils.beginTransaction();
			List<Order> order = orderDao.loadAll(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 */
	public void updateStatus(String oid, int status) {
		try {
			orderDao.updateStatus(oid, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 支付订单
	 * @param oid
	 * @param i
	 */
	public void updateStatusAll(String oid) {
		try {
			orderDao.updateStatusAll(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
}
