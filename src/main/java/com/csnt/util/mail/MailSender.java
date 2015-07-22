package com.csnt.util.mail;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送主体方法
 * @author tengyue
 */
public class MailSender {
	
	/**
	 * 使用单态模式防止,防止直接new SimpleMailSender对象，从而绕过相关检验规则
	 */
	private static MailSender simpleMailSenderInstance=null;
	public static MailSender getInstance(){
		if(null==simpleMailSenderInstance){
			synchronized(MailSender.class){
				simpleMailSenderInstance=new MailSender();
			}
		}
		return simpleMailSenderInstance;
	}
	private MailSender(){
		
	}

	/**
	 * 发送html格式的邮件
	 * @param mailInfo 邮件实体信息
	 * @return true：发送成功； false: 发送失败
	 * @throws MailException 邮件发送异常
	 */
    public boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception {
    	return sendMail(mailInfo,false);
    }
    

	/**
	 * 发送text格式的邮件
	 * @param mailInfo 邮件实体信息
	 * @return true：发送成功； false: 发送失败
	 * @throws MailException 邮件发送异常
	 */
    public boolean sendTextMail(MailSenderInfo mailInfo) throws Exception {
    	return sendMail(mailInfo,true);
    }
    
    /**
     * 对text和html格式的邮件发送进行抽取
     * @param mailInfo 邮件封装实体
     * @param sendType 邮件发送类型 true：代表文本格式的邮件； false： 代表html格式的邮件
     * @return
     * @throws MailException 
     */
    private boolean sendMail(MailSenderInfo mailInfo, boolean sendType) throws Exception{

    	// 判断是否需要身份认证 
    	MyAuthenticator authenticator = null;
    	Properties pro = mailInfo.loadPropertiesSession();
    	if (mailInfo.isValidate()) {
    		// 如果需要身份认证，则创建一个密码验证器    
    		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
    	}
    	// 根据邮件会话属性和密码验证器构造一个发送邮件的session
    	Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
    	try {
    		// 根据session创建一个邮件消息 
    		Message mailMessage = new MimeMessage(sendMailSession);
    		// 创建邮件发送者地址 
    		Address from = new InternetAddress(mailInfo.getFromAddress());
    		// 设置邮件消息的发送者 
    		mailMessage.setFrom(from);
    		boolean emailAddress=false;
    		// 创建邮件的接收者地址，并设置到邮件消息中    
    		String toEmails=mailInfo.getToAddress().toString();
    		if(toEmails!=null && toEmails.length()>2){
    			toEmails=toEmails.substring(1, toEmails.length()-1);
    			//设置收件人的地址(设置群组)
    			mailMessage.setRecipients(Message.RecipientType.TO,(Address[]) InternetAddress.parse(toEmails));
    			emailAddress=true;
    		}
    		// 创建邮件的抄送者接收地址，并设置到邮件消息中 
    		String ccEmails=mailInfo.getCopyTo().toString();
    		if(ccEmails!=null && ccEmails.length()>2){
    			ccEmails=ccEmails.substring(1, ccEmails.length()-1);
    			//设置抄送人的地址(设置群组)
    			mailMessage.setRecipients(Message.RecipientType.CC,(Address[]) InternetAddress.parse(ccEmails));
    			emailAddress=true;
    		}
    		//创建邮件的暗抄送人接收地址，并设置到邮件信息中
    		String bccEmails = mailInfo.getBccCopyTo().toString();
    		if(bccEmails!=null && bccEmails.length()>2){
    			bccEmails = bccEmails.substring(1,bccEmails.length()-1);
    			//设置暗抄送人的地址(设置群组)
    			mailMessage.setRecipients(Message.RecipientType.BCC, (Address[]) InternetAddress.parse(bccEmails));
    			emailAddress = true;
    		}
    		if(!emailAddress){
    			throw new  Exception("必须指定一个收件人、抄送人或者暗抄送人邮箱地址!");
    		}
    		// 设置邮件消息的主题 
    		if(null!=mailInfo.getSubject())
    			mailMessage.setSubject(mailInfo.getSubject());
    		// 设置邮件消息发送的时间 ,设置当前时间,此处也可以不进行设置，则实际邮件收到者默认为当前发送时间
    		mailMessage.setSentDate(new Date());
    		//此处设置发送附件
    		Vector<String> file=mailInfo.getFile();
    		Multipart mp = new MimeMultipart();
    		MimeBodyPart mbp = new MimeBodyPart();
    		if(sendType){
    			if(mailInfo.getContent()!= null ){
        			mbp.setText(mailInfo.getContent().toString());
        		}else{
        			mbp.setText("");
        			
        		}
    		}else{
    			if(mailInfo.getContent()!= null ){
        			mbp.setContent(mailInfo.getContent().toString(), "text/html;charset=GBK");
        		}else{
        			mbp.setContent("", "text/html;charset=GBK");
        		} 
    		}
    		mp.addBodyPart(mbp);
    		String filename;
    		if (!file.isEmpty()) {// 有附件
    			Enumeration<String> efile = file.elements();
    			while (efile.hasMoreElements()) {
    				mbp = new MimeBodyPart();
    				filename = efile.nextElement().toString(); // 选择出每一个附件名
    				FileDataSource fds = new FileDataSource(filename); // 得到数据源
    				mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
    				mbp.setFileName(fds.getName()); // 得到文件名同样加到BodyPart
    				mp.addBodyPart(mbp);
    			}
    			file.removeAllElements();
    		}
    		mailMessage.setContent(mp); // Multipart加入到信件
    		// 发送邮件    
    		Transport.send(mailMessage);
    		return true; //未有异常即表示发送成功
    	} catch (MessagingException ex) {
    		throw new Exception("请检查如下邮件地址是否有错,收件人地址如下:"+mailInfo.getToAddress()+",抄送人地址如下:"+ mailInfo.getCopyTo()+",暗抄送人如下:"+ mailInfo.getBccCopyTo());
    	}
    }
}
