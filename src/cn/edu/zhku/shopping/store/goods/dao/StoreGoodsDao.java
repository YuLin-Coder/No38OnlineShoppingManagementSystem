package cn.edu.zhku.shopping.store.goods.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.pager.Expression;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.pager.PageConstants;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 店铺管理商品 持久层
 * @author Administrator
 *
 */
public class StoreGoodsDao {

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 查询店铺的二级分类下的全部商品  --通过二级分类 cid,sid
	 * @param cid
	 * @param sid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Goods> findByCategory(String cid, String sid, int pc) throws SQLException {


		//1.得到每页记录pc
		int ps=PageConstants.GOODS_PAGE_SIZE;

		//2.得到总记录书tr
		String sql="select count(*) from t_goods where cid=? and sid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(),cid,sid);
		int tr = number.intValue();
		
		//3.得到当前页记录beanList
		sql="select * from t_goods where cid=? and sid=? order by orderBy limit ?,?";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(cid);//分类id
		params.add(sid);//店铺id
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数
		List<Goods> beanList=qr.query(sql, new BeanListHandler<Goods>(Goods.class),params.toArray());
		
		//4.创建相应的PageBean,返回分页查询结果
		PageBean<Goods> pb=new PageBean<Goods>();
		
		//当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		
		return pb;
	}

	/**
	 * 用uid查询用户对象
	 * @param uid
	 * @return
	 * @throws SQLException 
	 */
	public Store StorefindByUid(String uid) throws SQLException {
		String sql="select * from t_store where uid=?";
		Store store = qr.query(sql, new BeanHandler<Store>(Store.class),uid);
		return store;
	}

	/**
	 * 通过  pc（当前页）和 sid,gname 调用service同名方法进行查询
	 * @param sid
	 * @param gname
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Goods> StorefindByGnameAndUid(String sid, String gname,
			int pc) throws SQLException {
		//1.得到每页记录pc
		int ps=PageConstants.GOODS_PAGE_SIZE;

		//2.得到总记录书tr
		
		//模糊查询
		//sql += " where t.realName like concat(?realName,'%')";
		//String sql="select count(*) from t_goods where gname like concat(?gname,'%') and sid=?";
		//String sql="select count(*) from t_goods where gname like='%?%' and sid=?";
		
		String sql="select count(*) from t_goods where gname like ? and sid=?";
		
		Number number = (Number)qr.query(sql, new ScalarHandler(),"%"+gname+"%",sid);
		int tr = number.intValue();
		
		//3.得到当前页记录beanList
		//sql="select * from t_goods where gname like concat(?gname,'%') and sid=? order by orderBy limit ?,?";
		sql="select * from t_goods where gname like ? and sid=? order by orderBy limit ?,?";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("%"+gname+"%");//商品名
		params.add(sid);//店铺id
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数
		List<Goods> beanList=qr.query(sql, new BeanListHandler<Goods>(Goods.class),params.toArray());
		
		//4.创建相应的PageBean,返回分页查询结果
		PageBean<Goods> pb=new PageBean<Goods>();
		
		//当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		
		return pb;
	}

	/**
	 * 添加商品
	 * @param goods
	 * @throws SQLException 
	 */
	public void add(Goods goods) throws SQLException {
		String sql = "insert into t_goods(gid,gname,price,currPrice," +
				"discount,description,pro_area,units," +
				"cid,image_w,image_b,sid)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {goods.getGid(),goods.getGname(),
				goods.getPrice(),goods.getCurrPrice(),goods.getDiscount(),
				goods.getDescription(),goods.getPro_area(),goods.getUnits(),
				goods.getCategory().getCid(),goods.getImage_w(),goods.getImage_b(),
				goods.getStore().getSid()};
		qr.update(sql, params);
	}

	/**
	 * 加载商品详细信息
	 * 
	 *  1.商品表，分类表，多表查询
	 * 2.把map中的数据映射到goods对象中
	 * 3.把map中的数据映射到category对象中
	 * 4.把map中的数据映射到store对象中
	 * 5.建立联系，返回goods对象
	 * @param gid
	 * @return
	 * @throws SQLException 
	 */
	public Goods findByGid(String gid) throws SQLException {
		
		// 1.商品表，分类表，多表查询
		String sql="select * from t_goods a,t_category b where a.cid=b.cid and a.gid=?";
		Map<String,Object> map=qr.query(sql, new MapHandler(),gid);
	    
		String sql2="select * from t_goods a,t_store b where a.sid=b.sid and a.gid=?";
		Map<String,Object> map2=qr.query(sql2, new MapHandler(),gid);
		
		// 2.把map中的数据映射到goods对象中
		Goods goods=CommonUtils.toBean(map, Goods.class);
		// 3.把map中的数据映射到category对象中
		Category category=CommonUtils.toBean(map, Category.class);
		//当 pid!=null,该此分类是二级分类
		if(map.get("pid")!=null){
			Category parent=new Category();
			parent.setCid((String)map.get("pid"));
			category.setParent(parent);
		}
		
		// 4.把map中的数据映射到store对象中
		Store store=CommonUtils.toBean(map2, Store.class);
		//Store store=CommonUtils.toBean(map, Store.class);
		// 5.建立联系，返回goods对象
		goods.setCategory(category);
		goods.setStore(store);
		return goods;
	}

	/**
	 * 查询店铺的一级分类和其下的所有二级分类信息
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category loadCategory(String cid) throws SQLException {
		//1.得到一级分类
		String sql="select pid from t_category where cid=?";
		String pid = (String) qr.query(sql, new ScalarHandler(),cid);
		
		//2.得到一级分类对象
		String sql2="select * from t_category where cid=?";
		Category category = qr.query(sql2, new BeanHandler<Category>(Category.class),pid);
		
		//3.得到二级分类对象列表
		String sql3="select * from t_category where pid=?";
		List<Category> children = qr.query(sql3, new BeanListHandler<Category>(Category.class),pid);
		
		//4.一级分类加载相应 的二级分类
		category.setChildren(children);
		
		return category;
	}

	/**
	 * 修改商品信息
	 * @param goods
	 * @throws SQLException 
	 */
	public void edit(Goods goods) throws SQLException {
	
		String sql = "update t_goods set gname=?,price=?,currPrice=?," +
				"discount=?,description=?,pro_area=?,units=?,cid=? where gid=?";
		Object[] params = {goods.getGname(),goods.getPrice(),goods.getCurrPrice(),
				goods.getDiscount(),goods.getDescription(),goods.getPro_area(),
				goods.getUnits(),goods.getCategory().getCid(),goods.getGid()};
		qr.update(sql, params);
	}

	/**
	 * 删除商品信息
	 * @param gid
	 * @throws SQLException 
	 */
	public void delete(String gid) throws SQLException {
		String sql = "delete from t_goods where gid=?";
		qr.update(sql, gid);
	}
}
