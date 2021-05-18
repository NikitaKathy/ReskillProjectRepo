package com.oms.product.validator;

import com.oms.product.dto.ProductDTO;
import com.oms.product.exception.ProductMsException;

public class ProductValidator {
	
	public static void validateProduct(ProductDTO product) throws ProductMsException {
		
		if(!validateName(product.getProductName()))
			throw new ProductMsException("Validator.VALIDATE_NAME");
		
		if(!validateDescription(product.getDescription()))
			throw new ProductMsException("Validator.VALIDATE_DESCRIPTION");
			
		if(!validatePrice(product.getPrice()))
			throw new ProductMsException("Validator.VALIDATE_PRICE");
		
		if(!validateStock(product.getStock()))
			throw new ProductMsException("Validator.VALIDATE_STOCK");
		
		if(!validateImage(product.getImage()))
			throw new ProductMsException("Validator.VALIDATE_IMAGE");
		
	}
	
	public static boolean validateName(String name)
	{
		String regex = "([A-Za-z]+([ ][A-Za-z]+)*){1,100}";
		
		if(name.matches(regex))
		{
			return true;
		}
		return false;
	}
	
	public static boolean validateDescription(String desc)
	{
		String regex = "([A-Za-z]+([ ][A-Za-z]+)*){1,500}";
		
		if(desc.matches(regex))
		{
			return true;
		}
		return false;
	}
	
	public static boolean validatePrice(Float price)
	{
		if(price >=200)
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean validateStock(Integer stock)
	{
		if(stock>=10)
		{
			return true;
		}
		return false;
	}
	
	public static boolean validateImage(String image)
	{
		String regex = "[A-Za-z]+[\\.](png|jpeg)";
		
		if(image.matches(regex))
			return true;

		return false;
	}

}
