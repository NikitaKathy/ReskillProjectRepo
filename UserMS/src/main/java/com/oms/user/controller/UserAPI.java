package com.oms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oms.user.dto.BuyerDTO;
import com.oms.user.dto.SellerDTO;
import com.oms.user.exception.UserMsException;
import com.oms.user.service.UserService;

@RestController
@RequestMapping(value = "userms")
@CrossOrigin
public class UserAPI {
	
	@Autowired
	private UserService userServiceNew;
	
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
	
	
}