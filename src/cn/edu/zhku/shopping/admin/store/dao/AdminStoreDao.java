package cn.edu.zhku.shopping.admin.store.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.edu.zhku.shopping.cart.domain.CartItem;
import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.pager.PageConstants;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.edu.zhku.shopping.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 管理员管理店铺持久层
 * @author Administrator
 *
 */
public class AdminStoreDao {

	private QueryRunner qr=new TxQueryRunner();
	
	/*
	 * 把一个Map映射成一个Store
	 */
	private Store toStore(Map<String,Object> map) {
		if(map == null || map.size() == 0) return null;
		
		Store store = CommonUtils.toBean(map, Store.class);
		Category category = CommonUtils.toBean(map, Category.class);
		User user = CommonUtils.toBean(map, User.class);
		
		store.setCategory(category);
		store.setUser(user);
		return store;
	}
	
	/*
	 * 把多个Map(List<Map>)映射成多个Store(List<Store>)
	 */
	private List<Store> toStoreList(List<Map<String,Object>> mapList) {
		List<Store> storeList = new ArrayList<Store>();
		for(Map<String,Object> map : mapList) {
			Store store = toStore(map);
			storeList.add(store);
		}
		return storeList;
	}
	
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
	public PageBean<Store> findAllStore(int pc) throws SQLException {
		//1.得到每页记录pc
		int ps=PageConstants.STORERIMFO_PAGE_SIZE;
		ArrayList<Object> params = new ArrayList<Object>();//参数
		//2.得到总记录数tr
		String sql="select count(*) from t_store";
		Number number = (Number)qr.query(sql, new ScalarHandler());
		int tr = number.intValue();
		
		//3.得到当前页记录beanList
		sql="select * from t_store s,t_category c,t_user u where s.cid=c.cid and s.uid=u.uid limit ?,?";
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数		
		//List<Store> beanList=qr.query(sql, new BeanListHandler<Store>(Store.class),params.toArray());
		List<Store> beanList=toStoreList(qr.query(sql, new MapListHandler(), params.toArray()));
		//4.创建相应的PageBean,返回分页查询结果
		PageBean<Store> pb=new PageBean<Store>();
		
		//当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		return pb;
	}
	
	/**
	 * 按店铺名搜索
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 */
	public Store selectStoreByName(String sname) throws SQLException {
		String sql="select * from t_store s,t_category c,t_user u where s.cid=c.cid and s.uid=u.uid and sname=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), sname);
		return toStore(map);
	}

	/**
	 * 所属用户名是否存在校验
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}

	/**
	 * 店铺名不同名校验
	 * @param sname
	 * @return
	 * @throws SQLException 
	 */
	public boolean ajaxValidateSname(String sname) throws SQLException {
		String sql = "select count(1) from t_store where sname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(),sname);
		return number.intValue() == 0;
	}

	/**
	 * 添加店铺
	 * @param store
	 * @return
	 * @throws SQLException 
	 */
	public void addStore(Store store) throws SQLException {
		String loginname=store.getUser().getLoginname();
		String sql="select uid from t_user where loginname=?";
		String uid = (String) qr.query(sql, new ScalarHandler(),loginname);
		
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
	 * 查询所有一级分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findCategory() throws SQLException {
		String sql="select * from t_category where pid is null";
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

	/**
	 * 查询店铺的店铺信息和用户信息
	 * @param sid
	 * @return
	 * @throws SQLException 
	 */
	public Store findStore(String sid) throws SQLException {
		String sql="select * from t_user a,t_store b,t_category c where a.uid=b.uid and b.cid=c.cid and b.sid=?";
		Map<String, Object> map = qr.query(sql, new MapHandler(),sid);
		
		User user=CommonUtils.toBean(map, User.class);
		Store store=CommonUtils.toBean(map, Store.class);
		Category category=CommonUtils.toBean(map, Category.class);
		store.setUser(user);
		store.setCategory(category);
		return store;
	}

	/**
	 * 修改店铺信息
	 * @param store
	 * @throws SQLException 
	 */
	public void editStoreById(String sname,String cid,String sid) throws SQLException {
		String sql="update t_store set sname=?,cid=? where sid=?";
		Object[] params={sname,cid,sid};
		qr.update(sql,params);
	}

	/**
	 * 查询店铺sid
	 * @param beforeName
	 * @return
	 * @throws SQLException 
	 */
	public String findStoreByName(String beforeName) throws SQLException {
		String sql="select sid from t_store where sname=?";
		String sid=(String)qr.query(sql, new ScalarHandler(),beforeName);
		return sid;
	}

	/**
	 * 删除店铺
	 * @param sid
	 * @throws SQLException 
	 */
	public void deleteStoreById(String sid) throws SQLException {
		String sql="select uid from t_store where sid=?";
		String uid=(String) qr.query(sql, new ScalarHandler(),sid);
		//删除店铺表
		String sql2="delete from t_store where sid=?";
		qr.update(sql2,sid);
		
		//修改用户开店状态
		String sql3="update t_user set isStore=? where uid=?";
		qr.update(sql3,0+"",uid);
	}

	
}
