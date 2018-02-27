package com.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.impl.CategoryServiceImpl;
import com.store.utils.BeanFactory;
import com.store.utils.JsonUtil;

/**
 * Servlet implementation class CategorySevlet
 */
public class CategoryServlet extends BaseServlet {

    /**
     * 查询所有的分类
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO String finAll
		//调用CategoryService，查找所有的分类 ，返回list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try {
			clist = cs.findAll();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		//将返回值转换成json格式，返回到页面上
		//request.setAttribute("clist", clist);
		String json = JsonUtil.list2json(clist);
		
		//
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
		
		return null;
	}

	
}

