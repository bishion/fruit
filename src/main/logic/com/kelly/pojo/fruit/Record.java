package com.kelly.pojo.fruit;

import java.util.Date;

/**
 * Record entity. @author MyEclipse Persistence Tools
 */

public class Record implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Float price;
	private Float subtotal;
	private OrderInfo orderInfo;
	private String fruitName;
	private String buyer;
	private Integer quantity;
	private Date buyDate;

	// Constructors

	/** default constructor */
	public Record() {
	}

	/** minimal constructor */
	public Record(String fruitName, Integer quantity) {
		this.fruitName = fruitName;
		this.quantity = quantity;
	}

	/** full constructor */
	public Record(OrderInfo orderInfo, String fruitName, Integer quantity) {
		this.orderInfo = orderInfo;
		this.fruitName = fruitName;
		this.quantity = quantity;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderInfo getOrderInfo() {
		return this.orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getFruitName() {
		return this.fruitName;
	}

	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

}