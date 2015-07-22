package com.csnt.util.mail;

import javax.mail.*;   
/**
 * 邮件身份认证验证器
 * @author tengyue
 */
public class MyAuthenticator extends Authenticator{
	String userName;
	String password;

	public MyAuthenticator() {
		super();
	}

	public MyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(userName, password);
	}
}
