package com.oms.user.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin
public class UserAPI {
	
	@Autowired
	private UserService userServiceNew;
	
	@Autowired
	DiscoveryClient client;
	
	@Autowired
	Environment environment;
	
	@PostMapping(value = "/userMS/buyer/register")
	public ResponseEntity<String> registerBuyer(@RequestBody BuyerDTO buyerDto){
		
		try {
		String s ="Buyer registered successfully with buyer Id : " + userServiceNew.buyerRegistration(buyerDto);
		return new ResponseEntity<>(s,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			String s = environment.getProperty(e.getMessage());
			return new ResponseEntity<>(s,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping(value = "/userMS/seller/register")
	public ResponseEntity<String> registerSeller(@RequestBody SellerDTO sellerDto){
		
		try {
		String s ="Seller registered successfully with seller Id : "+ userServiceNew.sellerRegistration(sellerDto);
		return new ResponseEntity<>(s,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			return new ResponseEntity<>(environment.getProperty(e.getMessage()),HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	@PostMapping(value = "/userMS/buyer/login/{email}/{password}")
	public ResponseEntity<String> loginBuyer(@PathVariable String email, @PathVariable String password)
	{
		try {
			String msg = userServiceNew.buyerLogin(email, password);
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}
		catch(UserMsException e)
		{
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = "/userMS/seller/login/{email}/{password}")
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
	
	@DeleteMapping(value = "/userMS/buyer/deactivate/{id}")
	public ResponseEntity<String> deleteBuyerAccount(@PathVariable String id){
		
		String msg = userServiceNew.deleteBuyer(id);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
		
		
	}
	
	@DeleteMapping(value = "/userMS/seller/deactivate/{id}")
	public ResponseEntity<String> deleteSellerAccount(@PathVariable String id){
		
		String msg = userServiceNew.deleteSeller(id);
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	@PostMapping(value = "/userMS/buyer/wishlist/add/{buyerId}/{prodId}")
	public ResponseEntity<String> addProductToWishlist(@PathVariable String buyerId, @PathVariable String prodId) throws UserMsException
	{
		try {
		List<ServiceInstance> instances=client.getInstances("PRODUCTMS");
		ServiceInstance instance=instances.get(0);
		URI productUri = instance.getUri();
		
		ProductDTO product = new RestTemplate().getForObject(productUri+"/prodMS/getById/"+prodId, ProductDTO.class);
		
		String msg = userServiceNew.wishlistService(product.getProdId(), buyerId);
		
		return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			System.out.println(e);
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product name";
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,newMsg,e);
		}
	}
	
	@PostMapping(value = "/userMS/buyer/cart/add/{buyerId}/{prodName}/{quantity}")
	public ResponseEntity<String> addProductToCart(@PathVariable String buyerId, @PathVariable String prodName, @PathVariable Integer quantity) throws UserMsException
	{
		try {
		List<ServiceInstance> instances=client.getInstances("PRODUCTMS");
		ServiceInstance instance=instances.get(0);
		URI productUri = instance.getUri();
		System.out.println(productUri);
		
		 
		ProductDTO product = new RestTemplate().getForObject(productUri+"/prodMS/getByName/"+prodName, ProductDTO.class);
		System.out.println(product);
		System.out.println(product instanceof ProductDTO);
		String msg = userServiceNew.cartService(product.getProdId(), buyerId, quantity);
		
		return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			String newMsg = "There was some error";
			if(e.getMessage().equals("404 null"))
			{
				newMsg = "There are no PRODUCTS for the given product name";
			}
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,newMsg,e);
		}
	}
	
	
	@GetMapping(value = "/userMS/buyer/cart/get/{buyerId}")
	public ResponseEntity<List<CartDTO>> getProductListFromCart(@PathVariable String buyerId) throws UserMsException
	{
		
		//ProductDTO product = new RestTemplate().getForObject("http://localhost:8100/prodMs/getByName/"+prodName, ProductDTO.class);
		try {
		List<CartDTO> list = userServiceNew.getCartProducts(buyerId);
		
		return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
		}
		catch(UserMsException e)
		{
			System.out.println(e.getMessage());
			String msg = e.getMessage();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
			
		}
	}
	
}