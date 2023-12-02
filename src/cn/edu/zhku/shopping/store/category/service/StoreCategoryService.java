package cn.edu.zhku.shopping.store.category.service;

import java.sql.SQLException;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.store.category.dao.StoreCategoryDao;
import cn.edu.zhku.shopping.store.store.domain.Store;

/**
 *  店铺分类模块控制层
 * @author Administrator
 *
 */
public class StoreCategoryService {

	private StoreCategoryDao storeCategoryDao =new StoreCategoryDao ();

	/**
	 * 查询所有分类，得出店铺分类菜单
	 * @param sid
	 * @param cid
	 * @return
	 */
	public Category findStoreAll(String sid) {
		try {
			return storeCategoryDao.findAll(sid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过用户id查询店铺对象信息
	 * @param loginname
	 * @return
	 */
	public Store findStoreId(String uid) {
		try {
			return storeCategoryDao.findStoreId(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过店铺id进行查询
	 * @param sid
	 * @return
	 */
	public Store findStoreById(String sid) {
		try {
			return storeCategoryDao.findStoreById(sid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
