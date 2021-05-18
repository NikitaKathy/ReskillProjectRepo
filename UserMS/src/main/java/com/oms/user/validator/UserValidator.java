package com.oms.user.validator;

import com.oms.user.dto.BuyerDTO;
import com.oms.user.dto.SellerDTO;
import com.oms.user.exception.UserMsException;

public class UserValidator {
	
	public static void validateBuyer(BuyerDTO buyer) throws UserMsException {
		
		if(!validateName(buyer.getName()))
			throw new UserMsException("Validator.ENTER_VALID_NAME");
		
		if(!validateEmail(buyer.getEmail()))
			throw new UserMsException("Validator.ENTER_VALID_EMAIL");
		
		if(!validateContactNumber(buyer.getPhoneNumber()))
			throw new UserMsException("Validator.ENTER_VALID_NUMBER");
		
			
		if(!validatePassword(buyer.getPassword()))
			throw new UserMsException("Validator.Enter_VALID_PASSWORD");
		
	}
	
	public static void validateSeller(SellerDTO buyer) throws UserMsException {
		
		if(!validateName(buyer.getName()))
			throw new UserMsException("Validator.ENTER_VALID_NAME");
		
		if(!validateEmail(buyer.getEmail()))
			throw new UserMsException("Validator.ENTER_VALID_EMAIL");
		
		if(!validateContactNumber(buyer.getPhoneNumber()))
			throw new UserMsException("Validator.ENTER_VALID_NUMBER");
		
			
		if(!validatePassword(buyer.getPassword()))
			throw new UserMsException("Validator.Enter_VALID_PASSWORD");
		
	}
	
	
	public static boolean validateName(String name)
	{
		
		String regex = "[A-Za-z]+([ ][A-Za-z]+)*";
		
		if(name.matches(regex))
			return true;
		
		return false;
		
	}
	
	public static boolean validateEmail(String email)
	{
		String regex = "[A-za-z]+@[A-za-z]+[\\.]com";
		
		if(email.matches(regex))
			return true;
		
		return false;
	}
	
	public static boolean validateContactNumber(String contactNumber)
	{
		
		String regex = "[6,7,8,9][0-9]{9}";
		
		if(contactNumber.matches(regex))
			return true;
		
		return false;
	}
	
	public static boolean validatePassword(String password)
	{
		String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{7,20}$";
		
		if(password.matches(regex))
			return true;
		
		return false;
	}	

}