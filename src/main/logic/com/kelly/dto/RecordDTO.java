package com.kelly.dto;

import com.csnt.util.date.DateUtil;
import com.kelly.pojo.fruit.Record;


public class RecordDTO {
	private Integer id;
	private Float price;
	private Float subtotal;
	private String fruitName;
	private String buyer;
	private Integer quantity;
	private String buyDate;
	
	public RecordDTO(){
		super();
	}
	public RecordDTO(Record record){
		super();
		this.buyDate = DateUtil.toString(record.getBuyDate(), DateUtil.yyyy_MM_dd_HH_mm_ss);
		this.buyer = record.getBuyer();
		this.fruitName = record.getFruitName();
		this.id = record.getId();
		this.price = record.getPrice();
		this.quantity = record.getQuantity();
		this.subtotal = record.getSubtotal();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getFruitName() {
		return fruitName;
	}
	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	
}
