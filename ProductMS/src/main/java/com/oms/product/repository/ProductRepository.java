package com.oms.product.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oms.product.entity.Product;

public interface ProductRepository extends CrudRepository<Product, String> {
	
	public Product findByProdId(String id);
	
	public Product findByProductName(String name);
	
	public List<Product> findByCategory(String category);
	
	public List<Product> findAll();

}
