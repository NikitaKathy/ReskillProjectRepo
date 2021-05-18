package com.oms.user.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import com.oms.user.dto.BuyerDTO;
import com.oms.user.dto.CartDTO;
import com.oms.user.dto.ProductDTO;
import com.oms.user.dto.SellerDTO;
import com.oms.user.exception.UserMsException;
import com.oms.user.service.UserService;

@RestController
@RequestMapping(value = "userms")
@CrossOrigin
public class UserAPI {
	
	@Autowired
	private UserService userServiceNew;
	
	@Autowired
	DiscoveryClient client;
	
	@PostMapping(value = "/buyer/register")
	public ResponseEntity<String> registerBuyer(@RequestBody BuyerDTO buyerDto){
		
		try {
		String s = userServiceNew.buyerRegistration(buyerDto);
		return new ResponseEntity<String>(s,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping(value = "/seller/register")
	public ResponseEntity<String> registerSeller(@RequestBody SellerDTO sellerDto){
		
		try {
		String s = userServiceNew.sellerRegistration(sellerDto);
		return new ResponseEntity<String>(s,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	@PostMapping(value = "/buyer/login/{email}/{password}")
	public ResponseEntity<String> loginBuyer(@PathVariable String email, @PathVariable String password)
	{
		try {
			String msg = userServiceNew.buyerLogin(email, password);
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/seller/login/{email}/{password}")
	public ResponseEntity<String> loginSeller(@PathVariable String email, @PathVariable String password)
	{
		try {
			String msg = userServiceNew.sellerLogin(email, password);
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/buyer/deactivate/{id}")
	public ResponseEntity<String> deleteBuyerAccount(@PathVariable String id){
		
		String msg = userServiceNew.deleteBuyer(id);
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
		
		
	}
	
	@DeleteMapping(value = "/seller/deactivate/{id}")
	public ResponseEntity<String> deleteSellerAccount(@PathVariable String id){
		
		String msg = userServiceNew.deleteSeller(id);
		
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@PostMapping(value = "/buyer/wishlist/add/{buyerId}/{prodName}")
	public ResponseEntity<String> addProductToWishlist(@PathVariable String buyerId, @PathVariable String prodName)
	{
		List<ServiceInstance> instances=client.getInstances("PRODUCTMS");
		ServiceInstance instance=instances.get(0);
		URI productUri = instance.getUri();
		
		ProductDTO product = new RestTemplate().getForObject(productUri+"/prodMs/getByName/"+prodName, ProductDTO.class);
		
		String msg = userServiceNew.wishlistService(product.getProdId(), buyerId);
		
		return new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "/buyer/cart/add/{buyerId}/{prodName}/{quantity}")
	public ResponseEntity<String> addProductToCart(@PathVariable String buyerId, @PathVariable String prodName, @PathVariable Integer quantity)
	{
		List<ServiceInstance> instances=client.getInstances("PRODUCTMS");
		ServiceInstance instance=instances.get(0);
		URI productUri = instance.getUri();
		 
		ProductDTO product = new RestTemplate().getForObject(productUri+"/prodMs/getByName/"+prodName, ProductDTO.class);
		
		String msg = userServiceNew.cartService(product.getProdId(), buyerId, quantity);
		
		return new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping(value = "/buyer/cart/get/{buyerId}")
	public ResponseEntity<List<CartDTO>> getProductListFromCart(@PathVariable String buyerId) throws UserMsException
	{
		
		//ProductDTO product = new RestTemplate().getForObject("http://localhost:8100/prodMs/getByName/"+prodName, ProductDTO.class);
		try {
		List<CartDTO> list = userServiceNew.getCartProducts(buyerId);
		
		return new ResponseEntity<List<CartDTO>>(list,HttpStatus.ACCEPTED);
		}
		catch(UserMsException e)
		{
			System.out.println(e.getMessage());
			String msg = e.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
			
		}
	}
	
}