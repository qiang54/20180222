package com.store.service;

import com.store.domain.Order;
import com.store.domain.PageBean;
import com.store.domain.User;

public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception;

	Order getOrderById(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

}
