package com.example.designpattern.factorymethod;


public class SenderFactory {
	public static final String SENDER_TYPE_MAIL = "mail";
	public static final String SENDER_TYPE_SMS = "sms";

	public Sender produceSender(String senderType) throws Exception {
		if (SENDER_TYPE_MAIL.equals(senderType)) {
			return new MailSender();
		} else if (SENDER_TYPE_SMS.equals(senderType)) {
			return new SmsSender();
		} else {
			throw new Exception("the senderType[" + senderType + "] not supported");
		}
	}
}
