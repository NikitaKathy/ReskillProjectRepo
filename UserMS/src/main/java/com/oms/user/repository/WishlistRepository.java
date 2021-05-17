package com.oms.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.oms.user.entity.Wishlist;
import com.oms.user.utility.CustomPK;


public interface WishlistRepository extends CrudRepository<Wishlist, CustomPK> {

}