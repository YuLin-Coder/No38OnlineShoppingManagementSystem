package cn.edu.zhku.shopping.admin.category.service;

import java.sql.SQLException;
import java.util.List;

import cn.edu.zhku.shopping.admin.category.dao.AdminCategoryDao;
import cn.edu.zhku.shopping.category.domain.Category;

/**
 * 管理员分类模块业务层
 * @author Administrator
 *
 */
public class AdminCategoryService {

	private AdminCategoryDao adminCategoryDao=new AdminCategoryDao();

	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		try {
			return adminCategoryDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加一级分类
	 * @param parent
	 */
	public void addParent(Category category) {
		try {
			adminCategoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询所有相应的父分类
	 * @param pid
	 * @return
	 */
	public List<Category> findParents() {
		try {
			return adminCategoryDao.findParents();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加二级分类
	 * @param parent
	 */
	public void addChild(Category category) {
		try {
			adminCategoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取一级分类的详细信息，通过cid获得该category对象
	 * @param cid
	 * @return
	 */
	public Category findCategory(String cid) {
		try {
			return adminCategoryDao.findCategory(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一级分类
	 * @param category
	 */
	public void editParent(Category category) {
		try {
			adminCategoryDao.editParent(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改二级分类
	 * @param child
	 */
	public void editChild(Category child) {
		try {
			adminCategoryDao.editChild(child);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询二级分类个数
	 * @param cid
	 * @return
	 */
	public long childNum(String cid) {
		try {
			return adminCategoryDao.childNum(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除父分类--一级分类
	 * @param cid
	 */
	public void deleteParent(String cid) {
		try {
			adminCategoryDao.deleteParent(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询二级分类下商品个数
	 * @param cid
	 * @return
	 */
	public long goodsNum(String cid) {
		try {
			return adminCategoryDao.goodsNum(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除二级分类
	 * @param cid
	 */
	public void deleteChild(String cid) {
		try {
			adminCategoryDao.deleteChild(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
