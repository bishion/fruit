package com.kelly.pojo.fruit;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

public class OrderInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer orderNo;
	private String buyer;
	private Float amount;
	private String phone;
	private Date buyDate;
	private String buyerId;
	private Set<Record> records = new HashSet<Record>(0);

	// Constructors

	/** default constructor */
	public OrderInfo() {
	}

	/** minimal constructor */
	public OrderInfo(String buyer, Float amount, String phone) {
		this.buyer = buyer;
		this.amount = amount;
		this.phone = phone;
	}

	/** full constructor */
	public OrderInfo(String buyer, Float amount, String phone, Set<Record> records) {
		this.buyer = buyer;
		this.amount = amount;
		this.phone = phone;
		this.records = records;
	}

	// Property accessors

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getBuyer() {
		return this.buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Float getAmount() {
		return this.amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Record> getRecords() {
		return this.records;
	}

	public void setRecords(Set<Record> records) {
		this.records = records;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

}