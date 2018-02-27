package com.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.User;

public class PrivateFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * 只重写此函数即可
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {

		//强转
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		//业务逻辑
		User user = (User) request.getSession().getAttribute("user");
		if( user == null){
			request.setAttribute("msg", "没有权限，请先登录");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return ;
		}
		//执行
		arg2.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
