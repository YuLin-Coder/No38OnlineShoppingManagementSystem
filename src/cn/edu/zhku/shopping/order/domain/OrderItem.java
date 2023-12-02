package cn.edu.zhku.shopping.order.domain;

import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.store.store.domain.Store;

/**
 * 订单条目实体层
 * @author Administrator
 *
 */
public class OrderItem {

	private String orderItemId;//主键
	private int quantity;//数量
	private double subtotal;//小计
	
	private Goods goods;//所属的商品
	private Order order;//所属的订单
	private Store store;//所属的店铺
	
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	
	
}
