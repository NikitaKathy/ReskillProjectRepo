package com.oms.order.service;

import java.util.List;

import com.oms.order.dto.CartDTO;
import com.oms.order.dto.OrderDTO;
import com.oms.order.dto.ProductDTO;
import com.oms.order.exception.OrderMsException;

public interface OrderService {
	
	public List<OrderDTO> viewAllOrders() throws OrderMsException;

	public String placeOrder(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO order) throws OrderMsException;

}
