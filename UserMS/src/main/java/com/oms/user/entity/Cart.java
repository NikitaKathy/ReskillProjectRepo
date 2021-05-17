package com.oms.user.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.oms.user.utility.CustomPK;

@Entity
@Table(name = "cart")
public class Cart {
	
	@EmbeddedId
	private CustomPK customPK;
	
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CustomPK getCustomPK() {
		return customPK;
	}

	public void setCustomPK(CustomPK customPK) {
		this.customPK = customPK;
	}

		
	
}
