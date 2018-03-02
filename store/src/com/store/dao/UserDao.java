package com.store.dao;

import java.sql.SQLException;

import com.store.domain.User;

public interface UserDao {

	void add(User user) throws Exception;
	
	User getUserByCode(String code) throws Exception;
	
	void update(User user) throws Exception;
	
	User getUserByUsernameAndPwd(String username, String password) throws Exception;

	

}
