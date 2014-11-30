package com.example.designpattern.factorymethod;


public class SmsSender implements Sender {
	@Override
	public void send() {
		System.out.println("this is smssender...");
	}
}
