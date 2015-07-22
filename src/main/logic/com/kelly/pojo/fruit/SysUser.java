package com.kelly.pojo.fruit;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */

public class SysUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String name;
	private String password;
	private String phone;

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(String username, String name, String password) {
		this.username = username;
		this.name = name;
		this.password = password;
	}

	/** full constructor */
	public SysUser(String username, String name, String password, String phone) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.phone = phone;
	}

	// Property accessors

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}