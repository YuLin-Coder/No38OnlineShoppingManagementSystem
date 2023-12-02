package cn.edu.zhku.shopping.store.goods.service;

import java.sql.SQLException;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.store.goods.dao.StoreGoodsDao;
import cn.edu.zhku.shopping.store.store.domain.Store;

/**
 * 店铺管理商品 业务层
 * @author Administrator
 *
 */
public class StoreGoodsService {

	private StoreGoodsDao storeGoodsDao=new StoreGoodsDao();

	/**
	 * 查询店铺的分类下的全部商品  --通过二级分类 cid,sid
	 * @param cid
	 * @param sid
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByCategory(String cid, String sid, int pc) {
		try {
			return storeGoodsDao.findByCategory(cid,sid,pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用uid查询用户对象
	 * @param uid
	 * @return
	 */
	public Store StorefindByUid(String uid) {
		try {
			return storeGoodsDao.StorefindByUid(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过  pc（当前页）和 sid,gname 调用service同名方法进行查询
	 * @param sid
	 * @param gname
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> StorefindByGnameAndUid(String sid, String gname,
			int pc) {
		try {
			return storeGoodsDao.StorefindByGnameAndUid(sid,gname,pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加商品
	 * @param goods
	 */
	public void add(Goods goods) {
		try {
			storeGoodsDao.add(goods);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载商品详细信息
	 * @param gid
	 * @return
	 */
	public Goods load(String gid) {
		try {
			return storeGoodsDao.findByGid(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询店铺的一级分类和其下的所有二级分类信息
	 * @param cid
	 * @return
	 */
	public Category loadCategory(String cid) {
		try {
			return storeGoodsDao.loadCategory(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改商品信息
	 * @param goods
	 */
	public void edit(Goods goods) {
		try {
			storeGoodsDao.edit(goods);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除商品信息
	 * @param gid
	 */
	public void delete(String gid) {
		try {
			storeGoodsDao.delete(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
