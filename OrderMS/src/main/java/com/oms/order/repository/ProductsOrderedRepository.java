package com.oms.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.oms.order.entity.ProductsOrdered;
import com.oms.order.utility.CustomPK;

public interface ProductsOrderedRepository extends CrudRepository<ProductsOrdered, CustomPK>{

}
