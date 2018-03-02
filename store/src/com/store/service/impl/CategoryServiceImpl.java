package com.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.CategoryDao;
import com.store.dao.ProductDao;
import com.store.dao.impl.CategoryDaoImpl;
import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;
import com.store.utils.DataSourceUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;





public class CategoryServiceImpl implements CategoryService {

	
	/**
	 * 查询所有分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		// TODO  findAll
		//1.创建缓存管理器
		CacheManager cm = CacheManager.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		//2.获取指定缓存
		Cache cache = cm.getCache("categoryCache");
		
		//3.通过缓存获取数据
		Element element = cache.get("clist");
		
		//4.判断
		List<Category> list = null;
		if(element == null){
			//若为空，则从数据库中获取
			CategoryDao cdao = new CategoryDaoImpl();
			list = cdao.findAll();
			
			//将list放入缓存
			cache.put(new Element("clist", list));
			
			System.out.println("缓存中没有数据，已在数据库中提取");
		}else{
			//若不为空，则直接使用即可
		    list = (List<Category>) element.getObjectValue();
		    System.out.println("缓存中有数据");
		}
		
		return list;
	}

	/**
	 * 添加分类
	 */
	@Override
	public void add(Category c) throws Exception {

		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		categoryDao.add(c);
		
		//更新缓存
		//1.创建缓存管理器
		CacheManager cm = CacheManager
				.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));

		// 2.获取指定缓存
		Cache cache = cm.getCache("categoryCache");
		
		//3.清空缓存
		cache.remove("clist");
		
	}

	/**
	 * 删除分类
	 * @throws Exception 
	 */
	@Override
	public void delete(String cid) throws Exception{
		
		//开启事务
		try {
			DataSourceUtils.startTransaction();
			//更新商品
			ProductDao pd = (ProductDao) BeanFactory.getBean("ProductDao");
			pd.updateCid(cid);
			
			//删除分类
			CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
			categoryDao.delete(cid);
			
			//事务提交S
			DataSourceUtils.commitAndClose();
			
			//清空缓存
			//1.创建缓存管理器
			CacheManager cm = CacheManager
					.create(CategoryDaoImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));

			// 2.获取指定缓存
			Cache cache = cm.getCache("categoryCache");

			// 3.清空缓存
			cache.remove("clist");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
		}
	}

}
