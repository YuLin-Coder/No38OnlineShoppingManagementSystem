package cn.edu.zhku.shopping.cart.domain;

import java.math.BigDecimal;

import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;

public class CartItem {
	private String cartItemId;// 主键
	private int quantity;// 数量
	private Goods goods;// 条目对应的图书
	private User user;// 所属用户
	private Store store;// 所属用户
	
	// 添加小计方法
	public double getSubtotal() {
		/*
	    * 使用BigDecimal不会有误差
		* 要求必须使用String类型构造器
		*/
		BigDecimal b1 = new BigDecimal(goods.getCurrPrice() + "");
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}
		
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public User getUser() {
		return user;
	}
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
