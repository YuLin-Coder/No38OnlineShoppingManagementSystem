package cn.edu.zhku.shopping.store.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.order.domain.Order;
import cn.edu.zhku.shopping.order.domain.OrderItem;
import cn.edu.zhku.shopping.pager.Expression;
import cn.edu.zhku.shopping.pager.PageBean;
import cn.edu.zhku.shopping.pager.PageConstants;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 店铺订单模块持久层
 * @author Administrator
 *
 */
public class StoreOrderDao {

	private QueryRunner qr=new TxQueryRunner();

	/**
	 * 店铺查询所有自己的订单
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Order> findAll(String uid,int pc) throws SQLException {
		String sql="select sid from t_store where uid=?";
		String sid=(String) qr.query(sql, new ScalarHandler(),uid);
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("sid", "=", sid));
		return findByCriteria(exprList, pc);
	}
	
	private PageBean<Order> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
		/*
		 * 1. 得到ps
		 * 2. 得到tr
		 * 3. 得到beanList
		 * 4. 创建PageBean，返回
		 */
		/*
		 * 1. 得到ps
		 */
		int ps = PageConstants.ORDER_PAGE_SIZE;//每页记录数
		/*
		 * 2. 通过exprList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1"); 
		List<Object> params = new ArrayList<Object>();//SQL中有问号，它是对应问号的值
		for(Expression expr : exprList) {
			whereSql.append(" and ").append(expr.getName())
				.append(" ").append(expr.getOperator()).append(" ");
			if(!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}

		/*
		 * 3. 总记录数 
		 */
		String sql = "select count(*) from t_order" + whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();//得到了总记录数
		/*
		 * 4. 得到beanList，即当前页记录
		 */
		sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?,?";
		params.add((pc-1) * ps);//当前页首行记录的下标
		params.add(ps);//一共查询几行，就是每页记录数
		
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class), 
				params.toArray());
		
		// 虽然已经获取所有的订单，但每个订单中并没有订单条目。
		// 遍历每个订单，为其加载它的所有订单条目
		for(Order order : beanList) {
			loadOrderItem(order);
		}
		
		/*
		 * 5. 创建PageBean，设置参数
		 */
		PageBean<Order> pb = new PageBean<Order>();
		/*
		 * 其中PageBean没有url，这个任务由Servlet完成
		 */
		pb.setBeanList(beanList);    //当前页的所有订单记录
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		
		return pb;
	}

	/*
	 * 为指定的order载它的所有OrderItem
	 */
	private void loadOrderItem(Order order) throws SQLException {
		/*
		 * 1. 给sql语句select * from t_orderitem where oid=?
		 * 2. 执行之，得到List<OrderItem>
		 * 3. 设置给Order对象
		 */
		//得出店铺sid
		String sql="select sid from t_order where oid=?";
		String sid = (String) qr.query(sql, new ScalarHandler(),order.getOid());
		
		
		String sql2="select * from t_store where sid=?";
		Store store = qr.query(sql2, new BeanHandler<Store>(Store.class),sid);
		
		String sql3 = "select * from t_orderitem a,t_store b where a.sid=b.sid and a.oid=?";
		List<Map<String,Object>> mapList = qr.query(sql3, new MapListHandler(), order.getOid());
		
		//List<Map<String,Object>>  转化为     List<OrderItem>
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		order.setStore(store);
		order.setOrderItemList(orderItemList);
	}

	/**
	 * 把多个Map转换成多个OrderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	/*
	 * 把一个Map转换成一个OrderItem
	 */
	private OrderItem toOrderItem(Map<String, Object> map) {

		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		
		Goods goods = CommonUtils.toBean(map, Goods.class);
		
		Store store=CommonUtils.toBean(map, Store.class);
		
		orderItem.setGoods(goods);
		orderItem.setStore(store);
		return orderItem;
	}

	/**
	 * 按状态查询
	 * @param status
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByStatus(String uid,int status, int pc) throws SQLException {
		String sql="select sid from t_store where uid=?";
		String status2=status+"";
		String sid=(String) qr.query(sql, new ScalarHandler(),uid);
		
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status", "=", status2 + ""));
		exprList.add(new Expression("sid", "=", sid));
		return findByCriteria(exprList, pc);
	}

	/**
	 * 加载订单
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		loadOrderItem(order);//为当前订单加载它的所有订单条目
		return order;
	}

	/**
	 * 查询订单状态
	 * @param oid
	 * @return
	 * @throws SQLException 
	 */
	public int findStatus(String oid) throws SQLException {
		String sql = "select status from t_order where oid=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), oid);
		return number.intValue();
	}

	/**
	 * 修改订单状态
	 * @param oid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String oid, int status) throws SQLException {
		String sql = "update t_order set status=? where oid=?";
		qr.update(sql, status, oid);
	}
	
}
