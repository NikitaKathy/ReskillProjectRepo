package com.oms.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.oms.user.entity.Cart;
import com.oms.user.utility.CustomPK;

public interface CartRepository extends CrudRepository<Cart, CustomPK> {

}
