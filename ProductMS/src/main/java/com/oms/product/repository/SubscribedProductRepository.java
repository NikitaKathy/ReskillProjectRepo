package com.oms.product.repository;

import org.springframework.data.repository.CrudRepository;

import com.oms.product.entity.SubscribedProduct;
import com.oms.product.utility.CustomPK;

public interface SubscribedProductRepository extends CrudRepository<SubscribedProduct, CustomPK> {
	
	

}
