package com.store.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.ProductDao;
import com.store.domain.Product;
import com.store.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {

	/**
	 * 查询最新商品
	 */
	@Override
	public List<Product> findNewProd() throws Exception {
		// TODO findNewProd()
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	/**
	 * 查询热门商品
	 */
	@Override
	public List<Product> findHotProd() throws Exception {
		// TODO findHotProd()
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = 1 order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	/**
	 * 查询单个商品信息
	 */
	@Override
	public Product getProdById(String pid) throws Exception {
		// TODO getProdById(String pid)
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ? limit 1";
		return qr.query(sql,new BeanHandler<>(Product.class),pid);
	}

	/**
	 * 分类查找商品
	 */
	@Override
	public List findProdByPage(int currPage, int pageSize, String cid) throws Exception {
		// TODO findProdByPage(int currPage, int pageSize, String cid)
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ? ,?";
		return qr.query(sql, new BeanListHandler<>(Product.class), cid, (currPage - 1)*pageSize, pageSize);
	}

	/**
	 * 获取当前类别 商品总数目
	 */
	@Override
	public int getTotalCount(String cid) throws Exception {
		// TODO gettotalCount(String cid)
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), cid)).intValue();
	}

	/**
	 * 更新商品 ，为“删除分类 ”准备
	 */
	@Override
	public void updateCid(String cid) throws Exception {

		QueryRunner qr = new QueryRunner();
		String sql = "update product set cid = null where cid = ?";
		qr.update(DataSourceUtils.getConnection(), sql, cid);
	}

	/**
	 * 查找所有商品
	 */
	@Override
	public List<Product> findAll() throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product  ";
		
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	/**
	 * 增加商品
	 */
	@Override
	public void add(Product p) throws Exception {
		

		/*pid` varchar(32) NOT NULL,
		  `pname` varchar(50) DEFAULT NULL,
		  `market_price` double DEFAULT NULL,
		  `shop_price` double DEFAULT NULL,
		  `pimage` varchar(200) DEFAULT NULL,
		  `pdate` date DEFAULT NULL,
		  `is_hot` int(11) DEFAULT NULL,
		  `pdesc` varchar(255) DEFAULT NULL,
		  `pflag` int(11) DEFAULT NULL,
		  `cid` varchar(32) DEFAULT NULL,
		  */
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql, p.getPid(), p.getPname(), p.getMarket_price(),
				p.getShop_price(), p.getPimage(), p.getPdate(), 
				p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCategory().getCid());
	}

}
