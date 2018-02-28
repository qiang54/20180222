package com.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		
		request.setAttribute("list", list);
		
		return "/admin/category/list.jsp";

	
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/admin/category/add.jsp";
		
	}
	
	/**
	 * 增加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String cname = request.getParameter("cname");
		
		Category c = new Category();
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.add(c);
		
		//从定向
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
		
	}

	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String cid = request.getParameter("cid");
		
		
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.delete(cid);
		
		//从定向
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		
		return null;
		
	}

	

}
