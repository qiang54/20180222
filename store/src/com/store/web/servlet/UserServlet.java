package com.store.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.store.constant.Constant;
import com.store.domain.User;
import com.store.mycoverter.MyConverter;
import com.store.service.UserService;
import com.store.service.impl.UserServiceImpl;
import com.store.utils.BeanFactory;
import com.store.utils.MD5Utils;
import com.store.utils.UUIDUtils;

/**
 * 有关user的Servlet 
 */
/**
 * @author dell
 *
 */
public class UserServlet extends BaseServlet {

	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//System.out.println("UserServlet 的 add 方法执行了");
		return null;
	}


	/**
	 * 跳转注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("UserServlet 的 registerUI 方法执行了");
		return "/jsp/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		User user = new User();
		
	    //注册自定义转换器
		ConvertUtils.register(new MyConverter(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());
		
		//1.1设置用户id
		user.setUid(UUIDUtils.getId());
		
		//1.2设置激活码
		user.setCode(UUIDUtils.getCode());
		
		//1.3加密
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.调用service完成注册
		UserService s = new UserServiceImpl();
		s.regist(user);
		
		//3.页面请求转发
		request.setAttribute("msg", "用户已注册，请前往邮箱激活~~~");
		
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取激活码
		String code = request.getParameter("code");
		
		
		//2.调用service 完成激活
		UserService s = new UserServiceImpl();
		User user = s.active(code);
		
		if( user == null){
			request.setAttribute("msg", "请重新激活");
		}else{
			request.setAttribute("msg", "用户激活成功");
		}
		//3.请求转发到msg.jsp
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到登录UI
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/jsp/login.jsp";
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取用户名和密码
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    password = MD5Utils.md5(password);
	    
		//2.调用service完成登录操作
	    UserService s=(UserService) BeanFactory.getBean("UserService");
		User user = s.login(username,password);
		
		//3.判断用户是否为空
		if(user == null){
			//用户名，密码不匹配
			request.setAttribute("msg", "用户名，密码不匹配");
			return "/jsp/login.jsp";
		}else{
			//继续判断用户是否激活
			user.setState(1);
			if(Constant.USER_IS_ACTIVE != user.getState()){
				request.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
		}
		//4.将user放入session中重定向
		request.getSession().setAttribute("user", user);
	    response.sendRedirect(request.getContextPath() + "/"); //首页

		return null;
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//消除session
		request.getSession().invalidate();
		
		//重定向
		response.sendRedirect(request.getContextPath());
		//自动登录问题
		return null;
	}
}
