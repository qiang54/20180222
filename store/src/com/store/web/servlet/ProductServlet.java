package com.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.constant.Constant;
import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;

/**
 * 与商品有关的Servlet
 */
public class ProductServlet extends BaseServlet {

    /**
     * 通过id查询商品详情
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
	public String getProdById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO getProdById
		//获取商品id
		String pid = request.getParameter("pid");
		
		//调用service
		ProductService ps = new ProductServiceImpl();
		Product p = ps.getProdById(pid);
		
		//放入request域中，转发
		request.setAttribute("bean", p);
		
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * 分页查询商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findProdByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO findProdByPage
		//获取商品类别cid
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = Constant.PAGE_SIZE;
		
		//调用service
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> pb = ps.findProdByPage(currPage, pageSize, cid);
		
		//放入request域中，转发
		request.setAttribute("pBean", pb);
		
		return "/jsp/product_list.jsp";
	}

	
}
