package cn.edu.zhku.shopping.goods.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
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
 * 商品模块持久层
 * @author Administrator
 *
 */
public class GoodsDao {

	private static QueryRunner qr = new TxQueryRunner();
	//private QueryRunner qr=new TxQueryRunner();

	/**
	 * 主页菜单显示，按分类查
	 * 1.利用Expression拼接要查询的语句条件
	 * 2.利用通用查询方法，得到按分类查询的分页结果
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public static PageBean<Goods> findByCategory(String cid, int pc) throws SQLException {

		// 1.利用Expression拼接要查询的语句条件
		List<Expression> exprList=new ArrayList<Expression>();
		exprList.add(new Expression("cid","=",cid));
		// 2.利用通用查询方法，得到按分类查询的分页结果
		return findByCriteria(exprList, pc);
	}

	/**
	 * 通用的查询方法,得到相应的分页查询结果
	 * 1.得到每页记录pc
	 * 2.通过List<Expression> exprList生成相应where子句
	 * 3.得到总记录书tr
	 * 4.得到当前页记录beanList
	 * 5.创建相应的PageBean,返回分页查询结果
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	private static PageBean<Goods> findByCriteria(List<Expression> exprList,int pc) throws SQLException {
		
		//1.得到每页记录pc
		int ps=PageConstants.GOODS_PAGE_SIZE;
		//2.通过List<Expression> exprList生成相应where子句
		StringBuilder whereSql=new StringBuilder(" where 1=1");
		
		ArrayList<Object> params = new ArrayList<Object>();
		for(Expression expr:exprList){
			whereSql.append(" and ").append(expr.getName()).append(" ")
			.append(expr.getOperator()).append(" ");
			
			//如果Operator为is null，说明已经不需要条件了
			//两种情况：where cid is null  和   where cid = 3
			
			//情况一：where cid = 3
			if(!expr.getOperator().equals("is null")){
				whereSql.append("?");
			}
			params.add(expr.getValue());//相应参数位置上赋值
		}
		//3.得到总记录书tr
		String sql="select count(*) from t_goods"+whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();
		//4.得到当前页记录beanList
		sql="select * from t_goods"+whereSql+"order by orderBy limit ?,?";
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数
		List<Goods> beanList=qr.query(sql, new BeanListHandler<Goods>(Goods.class),params.toArray());
		//5.创建相应的PageBean,返回分页查询结果
		PageBean<Goods> pb=new PageBean<Goods>();
		
		//当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		
		return pb;
	}

	
	/**
	 * 分页查询方法二：
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	/*
    private static PageBean<Goods> findByCriteria2(String cid,int pc) throws SQLException {
		
		//1.得到每页记录pc
		int ps=PageConstants.GOODS_PAGE_SIZE;
		ArrayList<Object> params = new ArrayList<Object>();
		//2.得到总记录书tr
		String sql="select count(*) from t_goods where cid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), cid);
		int tr = number.intValue();
		//3.得到当前页记录beanList
		sql="select * from t_goods a ,t_store b where a.cid=? order by orderBy limit ?,?";
		params.add(cid);
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
	*/
	
	/**
	 * 以商品名称进行模糊查询
	 * 1.利用Expression拼接要查询的语句条件
	 * 2.利用通用查询方法，得到按分类查询的分页结果
	 * @param gname
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public static PageBean<Goods> findByGname(String gname, int pc) throws SQLException {
		// 1.利用Expression拼接要查询的语句条件
		List<Expression> exprList=new ArrayList<Expression>();
		exprList.add(new Expression("gname","like","%"+gname+"%"));
		// 2.利用通用查询方法，得到按分类查询的分页结果
		return findByCriteria(exprList, pc);
	}

	/**
	 * 按商品gid查询，加载商品详细信息
	 * 1.商品表，分类表，多表查询
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
	 * 以商品名称进行模糊查询  ---升序查询
	 * @param gname
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Goods> findByGnameUp(String gname, int pc) throws SQLException {
		//1.得到每页记录pc
		int ps=PageConstants.GOODS_PAGE_SIZE;
		
		//2.得到总记录数tr
		String sql="select count(*) from t_goods where gname like ? ";
		Number number = (Number)qr.query(sql, new ScalarHandler(), "%"+gname+"%");
		int tr = number.intValue();
		//3.得到当前页记录beanList
		sql="select * from t_goods where gname like ?  order by currPrice limit ?,? ";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("%"+gname+"%");
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数
		
		List<Goods> beanList=qr.query(sql, new BeanListHandler<Goods>(Goods.class),params.toArray());
		//4.创建相应的PageBean,返回分页查询结果
		PageBean<Goods> pb=new PageBean<Goods>();
		
		//5.当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		
		return pb;
	}

	/**
	 * 以商品名称进行模糊查询  ---降序查询
	 * @param gname
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Goods> findByGnameDown(String gname, int pc) throws SQLException {
		//1.得到每页记录pc
		int ps=PageConstants.GOODS_PAGE_SIZE;
		
		//2.得到总记录数tr
		String sql="select count(*) from t_goods where gname like ? ";
		Number number = (Number)qr.query(sql, new ScalarHandler(), "%"+gname+"%");
		int tr = number.intValue();
		//3.得到当前页记录beanList
		sql="select * from t_goods where gname like ?  order by currPrice desc limit ?,? ";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("%"+gname+"%");
		params.add((pc-1)*ps);//当前页记录的开始下标
		params.add(ps);//当前页记录数
		
		List<Goods> beanList=qr.query(sql, new BeanListHandler<Goods>(Goods.class),params.toArray());
		//4.创建相应的PageBean,返回分页查询结果
		PageBean<Goods> pb=new PageBean<Goods>();
		
		//5.当前PageBean中没设置url，url的设置有Servlet完成
		pb.setBeanList(beanList);//设置记录
		pb.setPc(pc);//设置当前页
		pb.setPs(ps);//设置每页记录
		pb.setTr(tr);//设置总记录
		
		return pb;
	}
}
