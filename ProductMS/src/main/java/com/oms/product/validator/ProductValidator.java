package com.oms.product.validator;

import com.oms.product.dto.ProductDTO;
import com.oms.product.exception.ProductMsException;

public class ProductValidator {
	
	public static void validateProduct(ProductDTO product) throws ProductMsException {
		
		if(!(validateName(product.getProductName())&&validateDescription(product.getDescription())&&validatePrice(product.getPrice())&&validateImage(product.getImage())&&validateStock(product.getStock())))
			throw new ProductMsException("Enter Valid Values");
		
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
