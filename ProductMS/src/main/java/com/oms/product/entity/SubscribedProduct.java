package com.oms.product.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.oms.product.utility.CustomPK;

@Entity
@Table(name = "subscribed_product")
public class SubscribedProduct {
	
	@EmbeddedId
	private CustomPK customId;

	public CustomPK getCustomId() {
		return customId;
	}

	public void setCustomId(CustomPK customId) {
		this.customId = customId;
	}
	
	

}
