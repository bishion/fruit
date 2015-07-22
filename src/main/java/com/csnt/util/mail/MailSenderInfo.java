package com.csnt.util.mail;

import java.util.Properties;
import java.util.Vector;

/**
 * 邮件信息实体类
 * @author tengyue
 */
public class MailSenderInfo {
	
	//发送邮件的服务器smtp
	private String mailServerHost;
	//发送邮件服务器端口号
	private String mailServerPort = "25";
	//邮件发送者的地址
	private String fromAddress;
	//邮件接收者的地址
	private Vector<String> toAddress = new Vector<String>();
	//抄送人
	private Vector<String> copyTo = new Vector<String>();
	//暗抄送人地址
	private Vector<String> bccCopyTo = new Vector<String>();
	//登陆邮件发送服务器的用户名
	private String userName;
	//登陆邮件发送服务器的密码
	private String password;
	//是否需要身份验证
	private boolean validate = false;
	//发送邮件主题
	private String subject;
	//发送邮件的内容
	private String content;
	//邮件内容格式类型
	private String contentType;
	//目前使用vector便于操作多个附件
	private Vector<String> file = new Vector<String>(); // 用于保存发送附件的文件名的集合
	
	/**
	 * 依据配置文件初始化相关邮件发送主体的信息
	 */
	public MailSenderInfo() {
		this.mailServerHost="smtp.163.com";
		this.mailServerPort="25";
		this.validate=true;
		this.userName="bishion";
		this.password="BIshion1234";
		this.fromAddress="bishion@163.com";	 
	}

	/**
	 * 加载邮件信息会话
	 */
	public Properties loadPropertiesSession(){
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false"); 
		return p;
	}
	
	public String getMailServerHost() {
		return mailServerHost;
	}
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}
	public String getMailServerPort() {
		return mailServerPort;
	}
	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public Vector<String> getToAddress() {
		return toAddress;
	}
	public void setToAddress(Vector<String> toAddress) {
		this.toAddress = toAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Vector<String> getFile() {
		return file;
	}
	public void setFile(Vector<String> file) {
		this.file = file;
	}
	public Vector<String> getCopyTo() {
		return copyTo;
	}
	public void setCopyTo(Vector<String> copyTo) {
		this.copyTo = copyTo;
	}
	public Vector<String> getBccCopyTo() {
		return bccCopyTo;
	}
	public void setBccCopyTo(Vector<String> bccCopyTo) {
		this.bccCopyTo = bccCopyTo;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	  该方法用于收集附件名(也可以通过直接set文件列名的vector)
	 */
	public void addAttachfile(String fname) {
		file.addElement(fname);
	}	
}
