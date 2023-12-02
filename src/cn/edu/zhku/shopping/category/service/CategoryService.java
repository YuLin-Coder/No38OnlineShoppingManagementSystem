package cn.edu.zhku.shopping.category.service;

import java.sql.SQLException;
import java.util.List;

import cn.edu.zhku.shopping.category.dao.CategoryDao;
import cn.edu.zhku.shopping.category.domain.Category;


/**
 * 分类模块业务层
 * @author Administrator
 *
 */
public class CategoryService {

	private CategoryDao categoryDao=new CategoryDao();

	/**
	 * 查询所有分类，得出主页分类菜单
	 * @return
	 */
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		try {
			return categoryDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
