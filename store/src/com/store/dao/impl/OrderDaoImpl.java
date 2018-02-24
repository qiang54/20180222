package com.store.dao.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.OrderDao;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.Product;
import com.store.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	/**
	 * 添加订单
	 */
	@Override
	public void add(Order order) throws Exception {

		QueryRunner  qr = new QueryRunner();
		/**
		 * `oid` varchar(32) NOT NULL,
		  `ordertime` datetime DEFAULT NULL,
		  `totalPrice` double DEFAULT NULL,
		  `state` int(11) DEFAULT NULL,
		  `address` varchar(30) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  `telephone` varchar(20) DEFAULT NULL,
		  `uid` varchar(32) DEFAULT NULL,
		 */
		String sql = "insert into orders values(?,?,?,?,?,?,?,?) ";
		qr.update(DataSourceUtils.getConnection(), sql, order.getOid(), order.getOrderTime(), order.getTotalPrice(), order.getState(), 
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
	    
	}

	/**
	 * 添加订单项
	 */
	@Override
	public void addItem(OrderItem orderItem) throws Exception {

		/**
		 * `itemid` varchar(32) NOT NULL,
		  `count` int(11) DEFAULT NULL,
		  `subtotal` double DEFAULT NULL,
		  `pid` varchar(32) DEFAULT NULL,
		  `oid` varchar(32) DEFAULT NULL,
		 */
		QueryRunner  qr = new QueryRunner();
		String sql = "insert into Orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, orderItem.getItemId(), orderItem.getCount(),orderItem.getSubTotal(),
				orderItem.getProduct().getPid(),orderItem.getOrder().getOid());
		
	}

	/**
	 * 分页查询“我的订单”
	 */
	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?, ?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid, (currPage - 1) * pageSize, pageSize);
		
		//遍历订单集合，封装每个订单的订单项列表
		sql = "select * from orderitem oi, product p where oi.pid = p.pid and oi.oid = ?";
		for (Order order : list) {
			//当前订单包含的所有内容
			List<Map<String, Object>> mList = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : mList) {
				//封装product 
				Product product = new Product();
				BeanUtils.populate(product, map);
				
				//封装orderItem
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				orderItem.setProduct(product);
				
				//将orderItem添加到对应的order对象中的items List中
				order.getItems().add(orderItem);
			}
			
			
		}
		return list;
	}

	/**
	 * 获取总订单数
	 */
	@Override
	public int getTotalCount(String uid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		
		return  ((Long)qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	@Override
	public Order findOrderById(String oid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
		
		sql = "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid = ? ";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : query) {
			//封装product
			Product product = new Product();
			BeanUtils.populate(product, map);
			
			//封装orderitem
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			
			//将orderitem 添加到order中
			order.getItems().add(orderItem);
		}
		return order;
	}

	

}
