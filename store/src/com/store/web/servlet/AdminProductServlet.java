package com.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Product;
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

}
