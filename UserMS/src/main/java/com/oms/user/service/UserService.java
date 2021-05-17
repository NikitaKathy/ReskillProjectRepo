package com.oms.user.service;

import com.oms.user.dto.BuyerDTO;
import com.oms.user.dto.SellerDTO;
import com.oms.user.exception.UserMsException;

public interface UserService {

	public String buyerRegistration(BuyerDTO buyerDTO) throws UserMsException;
	
	public String sellerRegistration(SellerDTO sellerDTO) throws UserMsException;
	
	public String buyerLogin(String email, String password) throws UserMsException;
	
	public String sellerLogin(String email, String password) throws UserMsException;
	
	public String deleteBuyer(String id);
	
	public String deleteSeller(String id);
	
}
