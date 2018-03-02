package com.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.CartItem;
import com.store.domain.Product;
import com.store.domain.Cart;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

/**
 * 购物车模块
 * @param <Cart>
 */
public class CartServlet extends BaseServlet {
	

	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	public Cart getCart(HttpServletRequest request){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//判断cart是否为空，第一次为空
		if(cart == null){
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
			
		return cart;
		
		
	}
	/**
	 * 添加到购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//获取pid /数量
		String pid = request.getParameter("pid");
		int  count = Integer.parseInt(request.getParameter("count"));
		//通过pid,获取一个商品product,调用ProductService
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		Product product = ps.getProdById(pid);
		
		//组装成CartItem
		CartItem cartItem = new CartItem(product, count);
		
		//添加到购物车
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		
		//重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * 从购物车中删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//1.获取删除商品的pid
		String pid = request.getParameter("pid");
		
		//2.调用购物车的remove()方法
		getCart(request).removeFromCart(pid);
		
		//3.重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
		
	}

	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//获取购物车，并清空
		getCart(request).clearCart();
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		return null;
		
	}
}
