package com.oms.product.utility;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomPK implements Serializable {
	
	protected String buyerId;
	protected String prodId;
	
	public CustomPK() {
		super();
	}

	public CustomPK(String buyerId, String prodId) {
		super();
		this.buyerId = buyerId;
		this.prodId = prodId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerId == null) ? 0 : buyerId.hashCode());
		result = prime * result + ((prodId == null) ? 0 : prodId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomPK other = (CustomPK) obj;
		if (buyerId == null) {
			if (other.buyerId != null)
				return false;
		} else if (!buyerId.equals(other.buyerId))
			return false;
		if (prodId == null) {
			if (other.prodId != null)
				return false;
		} else if (!prodId.equals(other.prodId))
			return false;
		return true;
	}
	
	

}
