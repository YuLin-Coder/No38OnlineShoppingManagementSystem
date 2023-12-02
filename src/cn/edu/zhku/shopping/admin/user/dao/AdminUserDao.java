package cn.edu.zhku.shopping.admin.user.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.Expression;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.pager.PageConstants;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 管理员管理用户持久层
 * @author Administrator
 *
 */
public class AdminUserDao {

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 普通查询方法
	 * 查询所有用户
	 * @return
	 * @throws SQLException 
	 */
	/*
	public List<User> findAllUser() throws SQLException {
		String sql="select * from t_user";
		List<User> userList=qr.query(sql, new BeanListHandler<User>(User.class));
		return userList;
	}
	*/
	/**
	 * 分页查询方法
	 * 查询所有用户
	 * 1.得到每页记录pc
	 * 2.得到总记录书tr
	 * 3.得到当前页记录beanList
	 * 4.创建相应的PageBean,返回分页查询结果
	 * @return
	 * @throws SQLException
	 */
	public PageBean<User> findAllUser(int pc) throws SQLException {
		//1.得到每页记录pc
		int ps=PageConstants.USERIMFO_PAGE_SIZE;
		ArrayList<Object> params = new ArrayList<Object>();//参数
		//2.得到总记录书tr
		String sql="select count(*) from t_user";
		Number number = (Number)qr.query(sql, new ScalarHandler());
		int tr = number.intValue();
		
		//3.得到当前页记录beanList
		sql="select * from t_user limit ?,?";
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数
		List<User> beanList=qr.query(sql, new BeanListHandler<User>(User.class),params.toArray());
		
		//4.创建相应的PageBean,返回分页查询结果
		PageBean<User> pb=new PageBean<User>();
		
		//当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		return pb;
	}

	/**
	 * 按用户名搜索
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 */
	public User selectUserByName(String loginname) throws SQLException {
		String sql="select * from t_user where loginname=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class),loginname);
		return user;
	}

	/**
	 * 通过uid查询用户信息，返回user对象
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public User findUser(String uid) throws SQLException {
		String sql="select * from t_user where uid=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class),uid);
		return user;
	}
        /**
	 * 修改用户信息
	 * @param formUser
	 * @throws SQLException 
	 */
	public void editUserById(User formUser,String uid) throws SQLException {
		String sql="update t_user set loginname=?,loginpass=?,email=?where uid=?";
		Object[] params={formUser.getLoginname(),formUser.getLoginpass(),formUser.getEmail(),uid};
		qr.update(sql,params);
		
//		int isStore=formUser.getIsStore();
//		//添加店铺
//		if(isStore!=0){
//			String sid=CommonUtils.uuid();
//		    String sql2="insert into t_store";
//		}
	}

	/**
	 * 删除用户信息
	 * @param uid
	 * @throws SQLException 
	 */
	public void deleteUserById(String uid) throws SQLException {
		String sql="delete from t_user where uid=?";
		qr.update(sql,uid);
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

	/**
	 * 以登录名进行判断是否存在该用户
	 * @param formUser
	 * @throws SQLException 
	 */
	public int findByLoginname(User formUser) throws SQLException {
		String sql="select count(*) from t_user where loginname=?";
		Object obj = qr.query(sql, new ScalarHandler(),formUser.getLoginname());
		Number number = (Number)obj;
		int cnt = number.intValue();
		return cnt;

	}

	/**
	 * 以邮箱进行判断是否存在该用户
	 * @param formUser
	 * @return
	 * @throws SQLException 
	 */
	public int findByEmail(User formUser) throws SQLException {
		String sql="select count(*) from t_user where email=?";
		Object obj = qr.query(sql, new ScalarHandler(),formUser.getEmail());
		Number number = (Number)obj;
		int cnt = number.intValue();
		return cnt;
	}

	/**
	 * 添加店铺
	 * @param store
	 * @return
	 * @throws SQLException 
	 */
	public void addStore(Store store) throws SQLException {
		String uid=store.getUser().getUid();

		//添加店铺表
		String sql2="insert into t_store values(?,?,?,?)";
		Object[] params={store.getSid(),store.getSname(),uid,store.getCategory().getCid()};
		qr.update(sql2,params);
		
		//修改用户表
		String sql3="update t_user set isStore=? where uid=?";
		Object[] params2={1+"",uid};
		qr.update(sql3,params2);
		
	}

	/**
	 * 找店铺sid
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public String findStore(String uid) throws SQLException {
		String sql="select sid from t_user a,t_store b where a.uid=b.uid and a.uid=?";
		String sid = (String) qr.query(sql, new ScalarHandler(),uid);
		return sid;
	}
}
