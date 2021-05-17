package com.oms.order.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.oms.order.utility.CustomPK;

@Entity
@Table(name = "products_ordered")
public class ProductsOrdered {
	
	@EmbeddedId
	private CustomPK primaryKeys;
	
	private String sellerId;	
	private Integer quantity;
	public CustomPK getPrimaryKeys() {
		return primaryKeys;
	}
	public void setPrimaryKeys(CustomPK primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
