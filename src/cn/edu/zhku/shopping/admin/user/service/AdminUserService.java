package cn.edu.zhku.shopping.admin.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.edu.zhku.shopping.admin.user.dao.AdminUserDao;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;

/**
 * 管理员管理用户业务层
 * @author Administrator
 *
 */
public class AdminUserService {

	private AdminUserDao adminUserDao=new AdminUserDao();

	/**
	 * 普通查询方法
	 * 查询所有用户
	 * @return
	 */
	/*
	public List<User> findAllUser() {
		try {
			return adminUserDao.findAllUser();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	*/
	/**
	 * 分页查询方法
	 * 查询所有用户
	 * @return
	 */
	public PageBean<User> findAllUser(int pc) {
		try {
			return adminUserDao.findAllUser(pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按用户名搜索
	 * @param loginname
	 * @return
	 */
	public User selectUserByName(String loginname) {
		try {
			return adminUserDao.selectUserByName(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过uid查询用户信息，返回user对象
	 * @param uid
	 * @return
	 */
	public User findUser(String uid) {
		try {
			return adminUserDao.findUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改用户信息
	 * @param formUser
	 */
	public void editUserById(User formUser,String uid) {
		try {
			adminUserDao.editUserById(formUser,uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除用户信息
	 * @param uid
	 */
	public void deleteUserById(String uid) {
		try {
			adminUserDao.deleteUserById(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 添加用户
	 * @param user
	 */
	public void regist(User user) {
		/*
		 * 1. 数据的补齐
		 */
		user.setUid(CommonUtils.uuid());
		/*
		 * 2. 向数据库插入
		 */
		try {
			adminUserDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 以登录名进行判断是否存在该用户
	 * @param formUser
	 * @return
	 */
	public int findByLoginname(User formUser) {
		try {
			return adminUserDao.findByLoginname(formUser);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 以邮箱进行判断是否存在该用户
	 * @param formUser
	 * @return
	 */
	public int findByEmail(User formUser) {
		try {
			return adminUserDao.findByEmail(formUser);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加店铺
	 * @param store
	 */
	public void addStore(Store store) {
		try {
			adminUserDao.addStore(store);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 找店铺sid
	 * @param uid
	 * @return
	 */
	public String findStore(String uid) {
		try {
			return adminUserDao.findStore(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
