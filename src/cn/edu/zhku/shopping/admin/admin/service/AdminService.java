package cn.edu.zhku.shopping.admin.admin.service;

import cn.edu.zhku.shopping.admin.admin.dao.AdminDao;
import cn.edu.zhku.shopping.admin.admin.domain.Admin;

import java.sql.SQLException;

/**
 * 管理员模块业务层
 * @author Administrator
 *
 */
public class AdminService {

	private AdminDao adminDao=new AdminDao();

	/**
	 * 登录
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin) {
		try {
			return adminDao.login(admin);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
