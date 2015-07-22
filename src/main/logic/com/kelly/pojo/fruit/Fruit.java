package com.kelly.pojo.fruit;

/**
 * Fruits entity. @author MyEclipse Persistence Tools
 */

public class Fruit implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Float price;
	private String remark;

	// Constructors

	/** default constructor */
	public Fruit() {
	}

	/** minimal constructor */
	public Fruit(String name, Float price) {
		this.name = name;
		this.price = price;
	}

	/** full constructor */
	public Fruit(String name, Float price, String remark) {
		this.name = name;
		this.price = price;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}