package com.example.designpattern.factorymethod;


public class MutliSenderFactory {
	public Sender produceMailSender() {
		return new MailSender();
	}

	public Sender produceSmsSender() {
		return new SmsSender();
	}
}
