package cn.edu.zhku.shopping.admin.store.service;

import java.sql.SQLException;
import java.util.List;

import cn.edu.zhku.shopping.admin.store.dao.AdminStoreDao;
import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;

/**
 * 管理员管理店铺业务层
 * 
 * @author Administrator
 * 
 */
public class AdminStoreService {

	private AdminStoreDao adminStoreDao = new AdminStoreDao();

	/**
	 * 分页查询方法 查询所有店铺
	 * 
	 * @return
	 */
	public PageBean<Store> findAllStore(int pc) {
		try {
			return adminStoreDao.findAllStore(pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按店铺名搜索
	 * 
	 * @param loginname
	 * @return
	 */
	public Store selectStoreByName(String sname) {
		try {
			return adminStoreDao.selectStoreByName(sname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 店铺名不同名校验
	 * 
	 * @param sname
	 * @return
	 */
	public boolean ajaxValidateSname(String sname) {
		try {
			return adminStoreDao.ajaxValidateSname(sname);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 所属用户名是否存在校验
	 * 
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return adminStoreDao.ajaxValidateLoginname(loginname);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加店铺
	 */
	public void addStore(Store store) {
		try {
			adminStoreDao.addStore(store);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询所有的一级分类
	 * 
	 * @return
	 */
	public List<Category> findCategory() {
		try {
			return adminStoreDao.findCategory();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询店铺的店铺信息和用户信息
	 * 
	 * @param sid
	 * @return
	 */
	public Store findStore(String sid) {
		try {
			return adminStoreDao.findStore(sid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改用店铺信息
	 * 
	 * @param store
	 */
	public void editStoreById(String sname, String cid, String sid) {
		try {
			adminStoreDao.editStoreById(sname, cid, sid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询店铺sid
	 * 
	 * @param beforeName
	 * @return
	 */
	public String findStoreByname(String beforeName) {
		try {
			return adminStoreDao.findStoreByName(beforeName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除店铺
	 * 
	 * @param sid
	 */
	public void deleteUserById(String sid) {
		try {
			adminStoreDao.deleteStoreById(sid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
