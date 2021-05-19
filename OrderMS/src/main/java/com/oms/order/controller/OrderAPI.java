package com.oms.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oms.order.dto.CartDTO;
import com.oms.order.dto.OrderDTO;
import com.oms.order.dto.OrderPlacedDTO;
import com.oms.order.dto.ProductDTO;
import com.oms.order.service.OrderService;

@RestController
public class OrderAPI {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	DiscoveryClient client;
	
	@Value("${user.uri}")
	String userUri;
	
	@Value("${product.uri}")
	String productUri;
	
	@PostMapping(value = "/orderMS/placeOrder/{buyerId}")
	public ResponseEntity<String> placeOrder(@PathVariable String buyerId, @RequestBody OrderDTO order){
		
		try {
//			List<ServiceInstance> userInstances=client.getInstances("USERMS");
//			ServiceInstance userInstance=userInstances.get(0);
//			URI userUri = userInstance.getUri();
			
			ObjectMapper mapper = new ObjectMapper();
			List<ProductDTO> productList = new ArrayList<>();
			List<CartDTO> cartList = mapper.convertValue(
					new RestTemplate().getForObject(userUri+"/userMS/buyer/cart/get/" + buyerId, List.class), 
				    new TypeReference<List<CartDTO>>(){}
				);
			
//			List<ServiceInstance> instances=client.getInstances("PRODUCTMS");
//			ServiceInstance instance=instances.get(0);
//			URI productUri = instance.getUri();
			
			cartList.forEach(item ->{
				ProductDTO prod = new RestTemplate().getForObject(productUri+"/prodMS/getById/" +item.getProdId(),ProductDTO.class) ; //getByProdId/{productId}
				System.out.println(prod.getDescription());
				productList.add(prod);
			});
			
			OrderPlacedDTO orderPlaced = orderService.placeOrder(productList,cartList,order);
			cartList.forEach(item->{
				new RestTemplate().getForObject(productUri+"/prodMS/updateStock/" +item.getProdId()+"/"+item.getQuantity(), boolean.class) ;
				new RestTemplate().postForObject(userUri+"/userMS/buyer/cart/remove/"+buyerId+"/"+item.getProdId(),null, String.class);
			});			
			
			new RestTemplate().getForObject(userUri+"/userMS/updateRewardPoints/"+buyerId+"/"+orderPlaced.getRewardPoints() , String.class);
			
			return new ResponseEntity<>(orderPlaced.getOrderId(),HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "Error while placing the order";
			}
			return new ResponseEntity<>(newMsg,HttpStatus.UNAUTHORIZED);
		}		
		
	}
	
	@GetMapping(value = "/orderMS/viewAll")
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
	
	@GetMapping(value = "/orderMS/viewOrders/{buyerId}")
	public ResponseEntity<List<OrderDTO>> viewsOrdersByBuyerId(@PathVariable String buyerId){		
		try {
			List<OrderDTO> allOrders = orderService.viewOrdersByBuyer(buyerId);
			return new ResponseEntity<>(allOrders,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}		
	}
	
	@GetMapping(value = "/orderMS/viewOrder/{orderId}")
	public ResponseEntity<OrderDTO> viewsOrderByOrderId(@PathVariable String orderId){		
		try {
			OrderDTO allOrders = orderService.viewOrder(orderId);
			return new ResponseEntity<>(allOrders,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}		
	}
	
	
	@PostMapping(value = "/orderMS/reOrder/{buyerId}/{orderId}")
	public ResponseEntity<String> reOrder(@PathVariable String buyerId, @PathVariable String orderId){
		
		try {
			
			String id = orderService.reOrder(buyerId,orderId);
			return new ResponseEntity<>("Order ID: "+id,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@PostMapping(value = "/orderMS/addToCart/{buyerId}/{prodId}/{quantity}")
	public ResponseEntity<String> addToCart(@PathVariable String buyerId, @PathVariable String prodId,@PathVariable Integer quantity){
		
		try {
			
//			List<ServiceInstance> userInstances=client.getInstances("USERMS");
//			ServiceInstance userInstance=userInstances.get(0);
//			URI userUri = userInstance.getUri();
			
			String successMsg = new RestTemplate().postForObject(userUri+"/userMS/buyer/cart/add/"+buyerId+"/"+prodId+"/"+quantity, null, String.class);

			return new ResponseEntity<>(successMsg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product ID";
			}
			return new ResponseEntity<>(newMsg,HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@PostMapping(value = "/orderMS/removeFromCart/{buyerId}/{prodId}")
	public ResponseEntity<String> removeFromCart(@PathVariable String buyerId, @PathVariable String prodId){
		
		try {
			
//			List<ServiceInstance> userInstances=client.getInstances("USERMS");
//			ServiceInstance userInstance=userInstances.get(0);
//			URI userUri = userInstance.getUri();
//			System.out.println(userUri);
			
			String successMsg = new RestTemplate().postForObject(userUri+"/userMS/buyer/cart/remove/"+buyerId+"/"+prodId, null, String.class);
			
			return new ResponseEntity<>(successMsg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product ID";
			}
			return new ResponseEntity<>(newMsg,HttpStatus.UNAUTHORIZED);
		}		
	}
	
	

}
