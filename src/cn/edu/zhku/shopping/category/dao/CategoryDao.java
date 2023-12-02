package cn.edu.zhku.shopping.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 分类模块持久层
 * @author Administrator
 *
 */
public class CategoryDao {

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 查询所有分类，得出主页分类菜单
	 * 1.查询出所有一级分类List<Map>类型
	 * 2.List<Map> 变为  List<Category>
	 * 3.循环遍历所有的一级分类，为每个一级分类加载它的二级分类 
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAll() throws SQLException {
		//1.查询出所有一级分类
		String sql="select * from t_category where pid is null order by orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
		//2.List<Map> 映射为  List<Category>
		List<Category> parents=toCategoryList(mapList);//一级分类集合
		
		//3.循环遍历所有的一级分类，为每个一级分类加载它的二级分类 
		for (Category parent:parents) {
			//查询出当前分类的子分类
			List<Category> children=findByParent(parent.getCid());
			//写到父分类
			parent.setChildren(children);
		}
		return parents;
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
	 * 通过父分类查询相应的子分类
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

}
