package cn.edu.zhku.shopping.store.store.service;

import java.sql.SQLException;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.store.store.dao.StoreDao;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;

public class StoreService {
	private StoreDao storeDao=new StoreDao();

	/**
	 * 用户开店
	 * 1.数据的补齐
	 * 2.向数据库插入
	 * @param store
	 */
	public void creatStore(Store store,String cid,String uid) {
		
		//1.数据的补齐
		store.setSid(CommonUtils.uuid());
		
		User user=new User();
		user.setUid(uid);
		
		Category category=new Category();
		category.setCid(cid);
		store.setUser(user);
		store.setCategory(category);
		//2.向数据库插入
		try {
			storeDao.createStore(store);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
