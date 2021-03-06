package com.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.store.dao.UserDao;
import com.store.domain.User;
import com.store.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	/**
	 * 用户注册
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/*`uid` varchar(32) NOT NULL,
		  `username` varchar(20) DEFAULT NULL,
		  `password` varchar(100) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  `email` varchar(30) DEFAULT NULL,
		  `telephone` varchar(20) DEFAULT NULL,
		  `birthday` date DEFAULT NULL,
		  `sex` varchar(10) DEFAULT NULL,
		  `state` int(11) DEFAULT NULL,
		  `code` varchar(64) DEFAULT NULL,
		  PRIMARY KEY (`uid`)
		  */
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), user.getState(),
				user.getCode());
		        
	
	
	}

	/**
	 * 通过code获取用户
	 */
	@Override
	public User getUserByCode(String code) throws Exception{
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), code);
	}

	/**
	 * 修改用户
	 */
	@Override
	public void update(User user) throws Exception{
		
		/*
		 * `uid` varchar(32) NOT NULL,
		  `username` varchar(20) DEFAULT NULL,
		  `password` varchar(100) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  `email` varchar(30) DEFAULT NULL,
		  `telephone` varchar(20) DEFAULT NULL,
		  `birthday` date DEFAULT NULL,
		  `sex` varchar(10) DEFAULT NULL,
		  `state` int(11) DEFAULT NULL,
		  `code` varchar(64) DEFAULT NULL,
		 */
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set username = ?, password = ?, name = ?, email = ?, "
				+ "birthday = ?, state = ?, code = ? where uid = ?";
		qr.update(sql, user.getUsername(), user.getPassword(), user.getName(),user.getEmail(),
				user.getBirthday(), user.getState(), null, user.getUid());
		
	}

	/**
	 * 通过用户名和密码获取用户
	 */
	@Override
	public User getUserByUsernameAndPwd(String username, String password) throws Exception {
		// TODO getUserByUsernameAndPwd
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
				
		return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}

	
	

}
