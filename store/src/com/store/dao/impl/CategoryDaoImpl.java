package com.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.store.dao.CategoryDao;
import com.store.dao.ProductDao;
import com.store.domain.Category;
import com.store.utils.BeanFactory;
import com.store.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {
	
	/**
	 * 查询所有分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		// TODO findAll List<Category>
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	/**
	 * 添加分类
	 */
	@Override
	public void add(Category c) throws Exception {

		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?,?)";
		
		qr.update(sql, c.getCid(), c.getCname());
	}

	/**
	 * 删除分类
	 */
	@Override
	public void delete(String cid) throws Exception {
		
		QueryRunner qr = new QueryRunner();
		String sql = "delete from category where cid = ?";
		qr.update(DataSourceUtils.getConnection(), sql, cid);
	}

	

}
