package com.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.CategoryService;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

/**
 * 后台商品管理相关
 */
public class AdminProductServlet extends BaseServlet {

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = ps.findAll();
		
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";

	}

	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		
		request.setAttribute("clist", list);
		
		return "/admin/product/add.jsp";
	}
}
