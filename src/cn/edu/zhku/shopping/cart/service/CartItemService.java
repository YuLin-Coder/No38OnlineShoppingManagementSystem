package cn.edu.zhku.shopping.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.edu.zhku.shopping.cart.dao.CartItemDao;
import cn.edu.zhku.shopping.cart.domain.CartItem;
import cn.itcast.commons.CommonUtils;

public class CartItemService {
	private CartItemDao cartItemDao = new CartItemDao();
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid) {
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加购物车条目
	 * @param cartItem
	 */
	public void add(CartItem cartItem){
		try{
			//查询该条目是否存在
			CartItem _cartItem=cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), cartItem.getGoods().getGid());
			if(_cartItem==null){//如果不存在，则创建购物车条目
				cartItem.setCartItemId(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			}else{//如果存在，则修改数量
				int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除与批量删除
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public void batchDelete(String cartItemIds){
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改购物车条目数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载多个cartItem
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds) {
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
