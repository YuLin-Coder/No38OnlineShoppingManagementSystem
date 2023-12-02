package cn.edu.zhku.shopping.store.store.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.jdbc.TxQueryRunner;

public class StoreDao {

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 用户开店,添加店铺
	 * 1.添加店铺
	 * 2.修改用户状态为开店
	 * @param store
	 * @throws SQLException 
	 */
	public void createStore(Store store) throws SQLException {
		//1.添加店铺
		String sql = "insert into t_store values(?,?,?,?)";
		Object[] params = {store.getSid(),store.getSname(),store.getUser().getUid(),store.getCategory().getCid()};
		qr.update(sql, params);
		//2.修改用户状态为开店
		String sql2 = "update t_user set isStore=? where uid=?";
		Object[] params2 = {1,store.getUser().getUid()};
		qr.update(sql2, params2);
	}
}
