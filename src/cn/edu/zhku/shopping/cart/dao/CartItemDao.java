package cn.edu.zhku.shopping.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.edu.zhku.shopping.cart.domain.CartItem;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDao {
	private QueryRunner qr = new TxQueryRunner();
	/*
	 * 把一个Map映射成一个Cartitem
	 */
	private CartItem toCartItem(Map<String,Object> map) {
		if(map == null || map.size() == 0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Goods goods = CommonUtils.toBean(map, Goods.class);
		User user = CommonUtils.toBean(map, User.class);
		
		Store store = CommonUtils.toBean(map, Store.class);
		
		cartItem.setGoods(goods);
		cartItem.setUser(user);
		cartItem.setStore(store);
		return cartItem;
	}
	
	/*
	 * 把多个Map(List<Map>)映射成多个CartItem(List<CartItem>)
	 */
	private List<CartItem> toCartItemList(List<Map<String,Object>> mapList) {
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		for(Map<String,Object> map : mapList) {
			CartItem cartItem = toCartItem(map);
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}
	/**
	 * 通过用户查询购物车条目
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public List<CartItem> findByUser(String uid) throws SQLException {
		String sql = "select * from t_cartitem c, t_goods g ,t_store s where c.gid=g.gid and c.sid=s.sid and c.uid=? order by c.orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), uid);
		return toCartItemList(mapList);
	}
	/**
	 * 查询某个用户的某个商品的购物车条目是否存在
	 * @param uid
	 * @param bid
	 * @return
	 * @throws SQLException 
	 */
	public CartItem findByUidAndBid(String uid, String gid) throws SQLException{
		String sql="select * from t_cartitem where uid=? and gid=?";
		Map<String,Object> map=qr.query(sql, new MapHandler(), uid,gid);
		CartItem cartItem=toCartItem(map);
		return cartItem;
	}
	/**
	 * 修改指定购物车条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException 
	 */
	public void updateQuantity(String cartItemId, int quantity) throws SQLException{
		String sql = "update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql, quantity, cartItemId);
	}
	/**
	 * 添加购物车条目
	 * @param cartItem
	 * @throws SQLException
	 */
	public void addCartItem(CartItem cartItem) throws SQLException{
		String sql = "insert into t_cartitem(cartItemId, quantity, gid, uid,sid)" +
				" values(?,?,?,?,?)";
		Object[] params = {cartItem.getCartItemId(), cartItem.getQuantity(),
				cartItem.getGoods().getGid(), cartItem.getUser().getUid(),cartItem.getStore().getSid()};
		qr.update(sql, params);
	}
	/**
	 * 删除与批量删除
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public void batchDelete(String cartItemIds) throws SQLException{
		/*
		 * 需要先把cartItemIds转换成数组
		 * 1. 把cartItemIds转换成一个where子句
		 * 2. 与delete from 连接在一起，然后执行
		 */
		Object[] cartItemIdArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		String sql = "delete from t_cartitem where " + whereSql;
		qr.update(sql, cartItemIdArray);
	}
	/*
	 * 用来生成where子句
	 */
	private String toWhereSql(int len) {
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i = 0; i < len; i++) {
			sb.append("?");
			if(i < len - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 按id查询
	 * @param cartItemId
	 * @return
	 * @throws SQLException 
	 */
	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartItem c, t_goods g where c.gid=g.gid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), cartItemId);
		return toCartItem(map);
	}
	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 * @throws SQLException 
	 */
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
		/*
		 * 1. 把cartItemIds转换成数组
		 */
		Object[] cartItemIdArray = cartItemIds.split(",");
		/*
		 * 2. 生成wehre子句
		 */
		String whereSql = toWhereSql(cartItemIdArray.length);
		/*
		 * 3. 生成sql语句
		 */
		String sql = "select * from t_cartitem c, t_goods g ,t_store s where c.gid=g.gid and c.sid=s.sid and " + whereSql;
		/*
		 * 4. 执行sql，返回List<CartItem>
		 */
		return toCartItemList(qr.query(sql, new MapListHandler(), cartItemIdArray));
	}
}
