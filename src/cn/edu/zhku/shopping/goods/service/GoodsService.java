package cn.edu.zhku.shopping.goods.service;

import java.sql.SQLException;

import cn.edu.zhku.shopping.goods.dao.GoodsDao;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.PageBean;

/**
 * 商品模块业务层
 * @author Administrator
 *
 */
public class GoodsService {

	private GoodsDao goodsDao=new GoodsDao();

	/**
	 * 主页菜单显示，按分类查
	 * @param cid
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByCategory(String cid, int pc) {

		try {
			return GoodsDao.findByCategory(cid,pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 以商品名称进行模糊查询
	 * @param gname
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByGname(String gname, int pc) {
		try {
			return GoodsDao.findByGname(gname,pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按商品gid查询，加载商品详细信息
	 * @param gid
	 * @return
	 */
	public Goods load(String gid) {
		try {
			return goodsDao.findByGid(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 以商品名称进行模糊查询  ---升序查询
	 * @param gname
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByGnameUp(String gname, int pc) {
		try {
			return goodsDao.findByGnameUp(gname,pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 以商品名称进行模糊查询  ---降序查询
	 * @param gname
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByGnameDown(String gname, int pc) {
		try {
			return goodsDao.findByGnameDown(gname,pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
