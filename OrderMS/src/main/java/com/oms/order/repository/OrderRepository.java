package com.oms.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.oms.order.entity.Order;

public interface OrderRepository extends CrudRepository<Order, String>{

}
