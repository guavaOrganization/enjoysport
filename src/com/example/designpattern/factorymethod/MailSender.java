package com.example.designpattern.factorymethod;


public class MailSender implements Sender {
	@Override
	public void send() {
		System.out.println("this is mailsneder...");
	}
}
