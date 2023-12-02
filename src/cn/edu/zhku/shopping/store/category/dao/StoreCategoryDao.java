package cn.edu.zhku.shopping.store.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 店铺分类模块持久层
 * 
 * @author Administrator
 * 
 */
public class StoreCategoryDao {

	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 查询出一级分类，一级相应的二级分类，得出店铺分类菜单
	 * 
	 * @param sid
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category findAll(String sid) throws SQLException {
		// 1.查询出所有一级分类
		String sql = "select * from t_category a,t_store b where a.cid=b.cid and b.sid=?";
//		Category parents = qr.query(sql, new BeanHandler<Category>(Category.class),sid);
		
		Map<String, Object> map = qr.query(sql, new MapHandler(),sid);
		Category parents = CommonUtils.toBean(map, Category.class);

		// 2.一级分类加载它的二级分类
		List<Category> children = findByParent(parents.getCid());
		// 写到父分类
		parents.setChildren(children);
		
		return parents;
	}

	/**
	 * 一级分类加载它的二级分类
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	private List<Category> findByParent(String pid) throws SQLException {

		/**
		 * 语法错误--select
		 * String sql="seclet * from t_category where pid=? order by OrderBy";
		 */
		
		String sql="select * from t_category where pid=? order by OrderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(),pid);
		//把List<Map> 映射为  List<Category>,返回
		return toCategoryList(mapList);
	}
	/**
	 * 把多个Map转换成多个Category
	 * List<Map> 映射为  List<Category>
	 * 1.创建  List<Category>
	 * 2.遍历每个map，一个map转换为category
	 * 3.转变后添加到List<Category>
	 * @param mapList
	 * @return
	 */
	private List<Category> toCategoryList(List<Map<String, Object>> mapList) {
		//1.创建  List<Category>
		ArrayList<Category> categoryList = new ArrayList<Category>();
		//2.遍历每个map，一个map转换为category
		for(Map<String,Object> map: mapList){
			Category category=toCategory(map);
			categoryList.add(category);
		}
		//3.转变后添加到List<Category>
		return categoryList;//一级分类集合
	}
	/**
	 * 把一个Map转换成一个Category
	 * Map 映射为 Category
	 * 1.封装数据
	 * 2.建立pid和cid参考映射关系
	 * @param map
	 * @return
	 */
	private Category toCategory(Map<String, Object> map) {

		//1.封装数据
		Category category = CommonUtils.toBean(map, Category.class);
		//2.建立pid和cid参考映射关系
		
		//如果是一级分类：pid=null,其下不存在二级分类，不需要建立映射关系
		String pid=(String)map.get("pid");
		
		//如果是二级分类，pid!=nul,其下存在二级分类，需要建立映射关系
		if(pid!=null){
			Category parent = new Category();
			parent.setCid(pid);//设置父分类
			category.setParent(parent);
		}
		
		return category;
	}

	/**
	 * 通过用户id查询店铺对象信息
	 * @param loginname
	 * @return
	 * @throws SQLException 
	 */
	public Store findStoreId(String uid) throws SQLException {
		String sql="select * from t_store  where uid=?";
		Store store = qr.query(sql, new BeanHandler<Store>(Store.class),uid);
		
		//第二次修改
//		String sql="select * from t_user a,t_store b where a.uid=b.uid and a.loginname=?";
//		Store store = qr.query(sql, new BeanHandler<Store>(Store.class),loginname);
    	//第一次修改
//		Map<String, Object> map = qr.query(sql, new MapHandler(),loginname);
//		String sid=(String) map.get("sid");
		return store;
	}

	/**
	 * 通过店铺sid查询店铺对象
	 * @param sid
	 * @return
	 * @throws SQLException 
	 */
	public Store findStoreById(String sid) throws SQLException {
		
		String sql="select * from t_store  where sid=?";
		Store store = qr.query(sql, new BeanHandler<Store>(Store.class),sid);
		return store;
	}

}
