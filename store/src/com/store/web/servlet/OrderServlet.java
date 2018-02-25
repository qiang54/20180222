package com.store.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;

import com.mysql.jdbc.PreparedStatement;
import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.PageBean;
import com.store.domain.User;
import com.store.mycoverter.MyConverter;
import com.store.service.OrderService;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;
import com.sun.jmx.snmp.Timestamp;

/**
 * 订单相关
 */
public class OrderServlet extends BaseServlet {

	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//判断用户是否登录
		User user = (User)request.getSession().getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "请先登录");
			return "/jsp/msg.jsp";
		}
		//1.封装数据
		Order order = new Order();
		//1.1订单id 
		order.setOid(UUIDUtils.getId());
		//1.2订单时间
		               //new  java.sql.Date(new Date().getTime())
		String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		order.setOrderTime(java.sql.Timestamp.valueOf(sdf));
		//1.3订单总金额
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		order.setTotalPrice(cart.getTotalPrice());
		
		//1.4订单包含所有订单项OrderItem
		Collection<CartItem> cartItems = cart.getCartItems();
		for (CartItem cartItem : cartItems) {
			
			/*
			 * 先取cart中的items
			 * 遍历items 组装成orderItem
			 * 将orderItem添加到list(items)中
			 */
			
			OrderItem oi = new OrderItem();
			oi.setItemId(UUIDUtils.getId());
			oi.setCount(cartItem.getCount());
			oi.setSubTotal(cartItem.getSubTotal());
			oi.setProduct(cartItem.getProduct());
			oi.setOrder(order);
			
			//添加到List中
			order.getItems().add(oi);
		}
		//1.5设置用户
		order.setUser(user);
		
		//2.调用service 添加订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);
		
		//3.将order放入request域中，请求转发页面跳转
		request.setAttribute("beanOrder", order);
		
		//4.清空购物车
		request.getSession().removeAttribute("cart");
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 分页查询“我的订单”
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//获取当前页
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 3;
		
		//获取用户
		User user = (User) request.getSession().getAttribute("user");
		if(user == null){
			request.setAttribute("msg", "你还没用登录，请先登录");
			return "/jsp/msg.jsp";
		}
		//调用service 分页查询
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> bean = os.findAllByPage(currPage, pageSize, user);
		
		//将pageBean 放入request域中
		request.setAttribute("pb", bean);
		
		return "/jsp/order_list.jsp";
		
	}
	
	/**
	 * 查看订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String oid = request.getParameter("oid");
		
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getOrderById(oid);
		
		request.setAttribute("beanOrder", order);
		
		return "/jsp/order_info.jsp";
		
	}
		

}
