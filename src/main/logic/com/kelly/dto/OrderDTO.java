package com.kelly.dto;

import java.util.List;

import com.csnt.util.date.DateUtil;
import com.kelly.pojo.fruit.OrderInfo;

public class OrderDTO {
	private Integer orderNo;
	private String buyer;
	private Float amount;
	private String phone;
	private String buyDate;
	private List<RecordDTO> recordList;
	
	public OrderDTO(){
		
	}
	
	public OrderDTO(OrderInfo orderInfo){
		this.orderNo = orderInfo.getOrderNo();
		this.buyer = orderInfo.getBuyer();
		this.amount = orderInfo.getAmount();
		this.phone = orderInfo.getPhone();
		this.buyDate = DateUtil.toString(orderInfo.getBuyDate(), DateUtil.yyyy_MM_dd_HH_mm_ss);
		
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}
	public List<RecordDTO> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<RecordDTO> recordList) {
		this.recordList = recordList;
	}
	
	
}
