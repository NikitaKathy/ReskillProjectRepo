package com.oms.order.service;

import java.util.List;

import com.oms.order.dto.CartDTO;
import com.oms.order.dto.OrderDTO;
import com.oms.order.dto.OrderPlacedDTO;
import com.oms.order.dto.ProductDTO;
import com.oms.order.exception.OrderMsException;

public interface OrderService {
	
	public List<OrderDTO> viewAllOrders() throws OrderMsException;

	public OrderPlacedDTO placeOrder(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO order) throws OrderMsException;

	public List<OrderDTO> viewOrdersByBuyer(String buyerId)throws OrderMsException;

	public OrderDTO viewOrder(String orderId) throws OrderMsException;

	public String reOrder(String buyerId, String orderId) throws OrderMsException;


}
