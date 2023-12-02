package cn.edu.zhku.shopping.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 用户模块持久层
 * @author lenovo
 *
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**1.用户注册
	 * 校验用户名是否注册
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		//String sql = "select count(1) from t_user where loginname=? collate utf8_bin";
		String sql = "select count(1) from t_user where binary loginname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}
	
	/**
	 * 校验Email是否注册
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), email);
		return number.intValue() == 0;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException 
	 */
	public void add(User user) throws SQLException {
		String sql = "insert into t_user values(?,?,?,?,?)";
		Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(),
				user.getEmail(),0+""};
		qr.update(sql, params);
	}
	
	/**2.用户登录
	 * 按用户名和密码查询
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException 
	 */
	public User findByLoginnameAndLoginpass(String loginname, String loginpass) throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
	}
	
	/**3.修改密码
	 * 按uid和password查询
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException 
	 */
	public boolean findByUidAndPassword(String uid, String password) throws SQLException {
		String sql = "select count(*) from t_user where uid=? and loginpass=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), uid, password);
		return number.intValue() > 0;
	}
	
	/**
	 * 修改密码
	 * @param uid
	 * @param password
	 * @throws SQLException
	 */
	public void updatePassword(String uid, String password) throws SQLException {
		String sql = "update t_user set loginpass=? where uid=?";
		qr.update(sql, password, uid);
	}
}
