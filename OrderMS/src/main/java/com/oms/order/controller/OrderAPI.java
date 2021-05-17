package com.oms.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.oms.order.dto.CartDTO;
import com.oms.order.dto.OrderDTO;
import com.oms.order.dto.ProductDTO;
import com.oms.order.service.OrderService;

@RestController
@RequestMapping(value = "/orderMs")
public class OrderAPI {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping(value = "/placeOrder/{buyerId}")
	public ResponseEntity<String> placeOrder(@PathVariable String buyerId, @RequestBody OrderDTO order){
		
		try {
			List<ProductDTO> productList = new ArrayList<>();
			@SuppressWarnings("unchecked")
			List<CartDTO> cartList=new RestTemplate().getForObject("http://localhost:8200/userMs/getCartByBuyerId/" + buyerId, List.class);
			
			cartList.forEach(item ->{
				ProductDTO prod = new RestTemplate().getForObject("http://localhost:8100/prodMs/getByProdId/" +item.getProdId(),ProductDTO.class) ; //getByProdId/{productId}
				productList.add(prod);
			});
			String orderId = orderService.placeOrder(productList,cartList,order);
			return new ResponseEntity<>(orderId,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
//			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
		}
		
	}
	
	@GetMapping(value = "/viewAll")
	public ResponseEntity<List<OrderDTO>> viewAllOrder(){
		
		try {
			List<OrderDTO> allOrders = orderService.viewAllOrders();
			return new ResponseEntity<>(allOrders,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
		
	}
	
	

}
