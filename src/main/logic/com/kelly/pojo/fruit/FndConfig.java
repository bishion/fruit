package com.kelly.pojo.fruit;

/**
 * FndConfig entity. @author MyEclipse Persistence Tools
 */

public class FndConfig implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private String cfgCode;
	private String cfgValue;
	private String remark;

	// Constructors

	/** default constructor */
	public FndConfig() {
	}

	/** minimal constructor */
	public FndConfig(String cfgCode, String cfgValue) {
		this.cfgCode = cfgCode;
		this.cfgValue = cfgValue;
	}

	/** full constructor */
	public FndConfig(String cfgCode, String cfgValue, String remark) {
		this.cfgCode = cfgCode;
		this.cfgValue = cfgValue;
		this.remark = remark;
	}

	// Property accessors

	public String getCfgCode() {
		return this.cfgCode;
	}

	public void setCfgCode(String cfgCode) {
		this.cfgCode = cfgCode;
	}

	public String getCfgValue() {
		return this.cfgValue;
	}

	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}