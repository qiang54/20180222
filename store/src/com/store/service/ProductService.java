package com.store.service;

import java.util.List;

import com.store.domain.PageBean;
import com.store.domain.Product;

public interface ProductService {

	List<Product> findNewProd() throws Exception;

	List<Product> findHotProd()  throws Exception;

	Product getProdById(String pid) throws Exception;

	PageBean<Product> findProdByPage(int currPage, int pageSize, String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product product) throws Exception;

}
