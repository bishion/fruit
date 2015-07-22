package com.csnt.util.mail;

import java.util.Vector;

public class MailTest {

	public static void main(String[] args) {
		MailSender sender = MailSender.getInstance();
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setContent("test");
		Vector<String> toAddress = new Vector<String>();
		toAddress.add("854907284@qq.com");
		mailInfo.setToAddress(toAddress );
		try {
			sender.sendTextMail(mailInfo );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
