package com.store.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {

	private Map<String, CartItem> map = new LinkedHashMap<>();//String id商品id，购物车项
	private double totalPrice = 0.0;
	
	/**
	 * 获取购物车项
	 * @return
	 */
	public Collection<CartItem> getCartItems(){
		
		return map.values();
	}
	/**
	 * 添加到购物车
	 * @param item
	 */
	public void add2Cart(CartItem item){
		
		//1.判断购物车中有无该商品
		String pid = item.getProduct().getPid();
		if(map.containsKey(pid)){
			//有，修改购买数量
			CartItem oItem = map.get(pid);
			oItem.setCount(oItem.getCount() + item.getCount());
		}else{
			//没有，则将商品添加即可
			map.put(pid, item);
		}
		//2.修改金额
		totalPrice += item.getSubTotal();
	}
	
	/**
	 * 删除商品
	 * @param pid
	 */
	public void removeFromCart(String pid){
		
		//删除
		CartItem item = map.remove(pid);
		
		//修改金额
		totalPrice -= item.getSubTotal();
	}
	
	
	/**
	 * 清空购物车
	 */
	public void clearCart(){
		
		map.clear();
		totalPrice = 0.0;
	}
	
	public Map<String, CartItem> getMap() {
		return map;
	}
	
	
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
}
