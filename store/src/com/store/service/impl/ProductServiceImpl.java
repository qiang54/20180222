package com.store.service.impl;

import java.util.List;

import com.store.dao.ProductDao;
import com.store.dao.impl.ProductDaoImpl;
import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	/**
	 * 查询最新商品
	 */
	@Override
	public List<Product> findNewProd() throws Exception {
		// TODO findnewProd()
		ProductDao pDao = new ProductDaoImpl();
		List<Product> newList = pDao.findNewProd();
		return newList;
	}

	/**
	 * 查询热门商品
	 */
	@Override
	public List<Product> findHotProd() throws Exception {
		// TODO findHotProd()
		ProductDao pDao = new ProductDaoImpl();
		List<Product> hotList = pDao.findHotProd();
		return hotList;
	}

	/**
	 * 查询单个商品详情
	 */
	@Override
	public Product getProdById(String pid) throws Exception {
		// TODO getProdById(String pid)
		ProductDao pDao = new ProductDaoImpl();
		Product p = pDao.getProdById(pid);
		return p;
	}

	/**
	 * 分类查找商品
	 */
	@Override
	public PageBean<Product> findProdByPage(int currPage, int pageSize, String cid) throws Exception {
		// TODO findProdByPage(int currPage, int pageSize, String cid)
		ProductDao pDao =(ProductDao) BeanFactory.getBean("ProductDao");
		//当前页数据
		List list = pDao.findProdByPage(currPage, pageSize, cid); 
		
		//总条数
		int totalCount = pDao.getTotalCount(cid);
		
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

	/**
	 * 查找所有商品
	 */
	@Override
	public List<Product> findAll() throws Exception {
		
		ProductDao pDao =(ProductDao) BeanFactory.getBean("ProductDao");
		return 	pDao.findAll();
	}

	/**
	 * 增加商品
	 */
	@Override
	public void add(Product product) throws Exception {
		
		ProductDao pDao = (ProductDao) BeanFactory.getBean("ProductDao");
		pDao.add(product);
	}

}
