package com.oms.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.order.dto.CartDTO;
import com.oms.order.dto.OrderDTO;
import com.oms.order.dto.ProductDTO;
import com.oms.order.entity.Order;
import com.oms.order.entity.ProductsOrdered;
import com.oms.order.exception.OrderMsException;
import com.oms.order.repository.OrderRepository;
import com.oms.order.repository.ProductsOrderedRepository;
import com.oms.order.utility.CustomPK;
import com.oms.order.validator.OrderValidator;

@Service(value = "orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductsOrderedRepository prodOrderedRepository;

	@Override
	public List<OrderDTO> viewAllOrders() throws OrderMsException {
		Iterable<Order> orders = orderRepository.findAll();
		List<OrderDTO> dtoList = new ArrayList<>();
		orders.forEach(order -> {
			OrderDTO odto = new OrderDTO();
			odto.setOrderId(order.getOrderId());
			odto.setBuyerId(order.getBuyerId());
			odto.setAmount(order.getAmount());
			odto.setAddress(order.getAddress());
			odto.setDate(order.getDate());
			odto.setStatus(order.getStatus());
			dtoList.add(odto);			
		});
		if(dtoList.isEmpty()) throw new OrderMsException("No orders available");
		return dtoList;
	}

	@Override
	public String placeOrder(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO orderDTO)
			throws OrderMsException {
		Order order = new Order();
		OrderValidator.validateOrder(orderDTO);
		order.setBuyerId(cartList.get(0).getBuyerId());
		order.setDate(LocalDate.now());
		order.setStatus("Order Placed");	
		List<ProductsOrdered> productsOrdered = new ArrayList<>();
		for(int i = 0; i<cartList.size();i++) {
			OrderValidator.validateStock(cartList.get(i), productList.get(i));			
			order.setAmount(order.getAmount()+(cartList.get(i).getQuantity()*productList.get(i).getPrice()));
			
			ProductsOrdered prodO = new ProductsOrdered();
			prodO.setSellerId(productList.get(i).getSellerId());
			prodO.setPrimaryKeys(new CustomPK(cartList.get(i).getBuyerId(),productList.get(i).getProdId()));
			prodO.setQuantity(cartList.get(i).getQuantity());
			productsOrdered.add(prodO);				
		}		
		prodOrderedRepository.saveAll(productsOrdered);
		orderRepository.save(order);
		
		return order.getOrderId();
	}

}
