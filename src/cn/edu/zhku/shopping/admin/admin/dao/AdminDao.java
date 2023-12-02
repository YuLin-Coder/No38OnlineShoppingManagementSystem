package cn.edu.zhku.shopping.admin.admin.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import cn.edu.zhku.shopping.admin.admin.domain.Admin;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 管理模块持久层
 * @author Administrator
 *
 */
public class AdminDao {

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 登录
	 * @param admin
	 * @return
	 * @throws SQLException 
	 */
	public Admin login(Admin admin) throws SQLException {
		String sql="select * from t_admin where adminname=? and adminpwd=?";
		Admin admin2 = qr.query(sql, new BeanHandler<Admin>(Admin.class),admin.getAdminname(),admin.getAdminpwd());
//		Map<String,Object> map=qr.query(sql,new MapHandler(),admin.getAdminname(),admin.getAdminpwd());
//		Admin admin2=CommonUtils.toBean(map, Admin.class);
		return admin2;
	}
	
}
