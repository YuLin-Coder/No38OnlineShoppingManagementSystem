package cn.edu.zhku.shopping.store.order.service;

import java.sql.SQLException;

import cn.edu.zhku.shopping.order.domain.Order;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.order.dao.StoreOrderDao;
import cn.itcast.jdbc.JdbcUtils;

/**
 * 店铺订单模块业务层
 * @author Administrator
 *
 */
public class StoreOrderService {

	private StoreOrderDao storeOrderDao=new StoreOrderDao();

	/**
	 * 店铺查询所有自己的订单
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findAll(String uid,int pc) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = storeOrderDao.findAll(uid,pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按状态查询
	 * @param status
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findByStatus(String uid,int status, int pc) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = storeOrderDao.findByStatus(uid,status, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			JdbcUtils.beginTransaction();
			Order order = storeOrderDao.load(oid);
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
			return storeOrderDao.findStatus(oid);
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
			storeOrderDao.updateStatus(oid, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
