package com.store.dao;

import java.util.List;

import com.store.domain.Product;

public interface ProductDao {

	List<Product> findNewProd() throws Exception;

	List<Product> findHotProd() throws Exception;

	Product getProdById(String pid) throws Exception;

	List findProdByPage(int currPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;

	void updateCid(String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product product) throws Exception;

}
