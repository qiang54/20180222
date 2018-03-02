package com.store.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 订单
 * @author qiang
 *
 */
public class Order implements Serializable {

	/**
	 * `oid` varchar(32) NOT NULL,
	  `ordertime` datetime DEFAULT NULL,
	  `total` double DEFAULT NULL,
	  `state` int(11) DEFAULT NULL,
	  `address` varchar(30) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  `telephone` varchar(20) DEFAULT NULL,
	  `uid` varchar(32) DEFAULT NULL,
	 */
	private String oid;
	private Date orderTime;
	private Double totalPrice;
	
	private int state;//订单状态，0-未支付；1-已支付；
	
	private String address;
	private String name;
	private String telephone;
	
	//订单用户
	private User user;
	
	//订单包含
	private  List<OrderItem> items = new LinkedList<>();

	public  List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
