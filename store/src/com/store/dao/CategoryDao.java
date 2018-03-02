package com.store.dao;

import java.util.List;

import com.store.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void add(Category c)  throws Exception;

	void delete(String cid) throws Exception;

	

}
