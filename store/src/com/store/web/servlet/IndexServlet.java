package com.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;

/**
 * 与首页有关的Servlet 
 */
public class IndexServlet extends BaseServlet {
	
	public String index(HttpServletRequest request, HttpServletResponse response) {
		
		//数据库中查询热门商品和最新商品，展示
		
		ProductService ps = new ProductServiceImpl();
		
		List<Product> newList = null;
		List<Product> hotList = null;
		
		try {
			//最新商品
			newList = ps.findNewProd();
			//热门商品
			 hotList = ps.findHotProd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//放入request域中
		request.setAttribute("newList", newList);
		request.setAttribute("hotList", hotList);
		
		return "/jsp/index.jsp";
	}

	
}
