package com.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.OrderDao;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.PageBean;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.utils.BeanFactory;
import com.store.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	/**
	 * 
	 */
	@Override
	public void add(Order order) throws Exception  {

		try {
			//开启事务
			DataSourceUtils.startTransaction();
			//向order中添加一条数据
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
			orderDao.add(order);
			
			//向OrderItem中添加n条数据
			for (OrderItem orderItem : order.getItems()) {
				orderDao.addItem(orderItem);
			}
			//提交并关闭事务
			DataSourceUtils.commitAndClose();
			
		} catch (SQLException e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		// TODO Auto-generated method stub
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		
		List<Order> list = orderDao.findAllByPage(currPage, pageSize, user.getUid());
		
		int totalCount = orderDao.getTotalCount(user.getUid());
		
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

	/**
	 * 查询订单详情
	 */
	@Override
	public Order getOrderById(String oid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		
		return orderDao.findOrderById(oid);
	}

}
