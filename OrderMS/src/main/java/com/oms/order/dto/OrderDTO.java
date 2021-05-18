package com.oms.order.dto;

import java.time.LocalDate;

public class OrderDTO {
	private String orderId;
	private String buyerId;
	private Float amount;
	private LocalDate date;
	private String address;
	private String status;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String addrerss) {
		this.address = addrerss;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String string) {
		this.status = string;
	}	
}
