package com.oms.user.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.oms.user.utility.CustomPK;

@Entity
@Table(name = "cart")
public class Cart {
	
	@EmbeddedId
	private CustomPK customId;

	public CustomPK getCustomId() {
		return customId;
	}

	public void setCustomId(CustomPK customId) {
		this.customId = customId;
	}

		
	
}
