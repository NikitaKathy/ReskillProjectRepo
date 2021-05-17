package com.oms.order.utility;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomPK implements Serializable {
	protected String buyerId;
	protected String productId;
	public CustomPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomPK(String buyerId, String productId) {
		super();
		this.buyerId = buyerId;
		this.productId = productId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyerId == null) ? 0 : buyerId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}	
	
	
	

}
