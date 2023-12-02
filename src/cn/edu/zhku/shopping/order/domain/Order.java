package cn.edu.zhku.shopping.order.domain;

import java.util.List;

import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;

/**
 * 订单模块实体层
 * @author Administrator
 *
 */
public class Order {

	private String oid;//订单主键
	private String ordertime;//下单时间
	private double total;//合计金额
	//5状态---1未付款, 2已付款但未发货, 3已发货未确认收货, 4确认收货了,交易成功, 5已取消(只有未付款才能取消)
	private int status;//订单状态
	private String address;//收货地址
	private User user;//订单所属用户
	private Store store;//订单所属店铺
	
	private List<OrderItem> orderItemList;//订单下的所有订单条目

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	
}
