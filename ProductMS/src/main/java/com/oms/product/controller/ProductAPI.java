package com.oms.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.oms.product.dto.ProductDTO;
import com.oms.product.service.ProductService;

@RestController
@RequestMapping("/prodMs")
public class ProductAPI {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping(value = "/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO prod){
		
		try {
			String msg = productService.addProduct(prod);
			return new ResponseEntity<String>(msg,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@GetMapping(value = "/getByName/{name}")
	public ResponseEntity<ProductDTO> getByProductName(@PathVariable String name)
	{
		System.out.println("Entered here");
		try {
			ProductDTO productDTO = productService.getProductByName(name);
			return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping(value = "/getById/{id}")
	public ResponseEntity<ProductDTO> getByProductId(@PathVariable String id)
	{
		try {
			ProductDTO productDTO = productService.getProductById(id);
			return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@GetMapping(value = "/getByCategory/{name}")
	public ResponseEntity<List<ProductDTO>> getByProductCategory(@PathVariable String name)
	{
		System.out.println("Entered here");
		try {
			List<ProductDTO> productDTO = productService.getProductByCategory(name);
			return new ResponseEntity<List<ProductDTO>>(productDTO,HttpStatus.OK);
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@DeleteMapping(value = "/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable String id){
		
		try {
			String msg = productService.deleteProduct(id);
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

}
