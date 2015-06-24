package com.example.designpattern.observer;

public class ObserverTest {
	public static void main(String[] args) {
		Twitter twitter = new Twitter();
		
		Audit audit = new Audit(twitter);
		Message message = new Message(twitter);
		
		twitter.attach(audit);
		twitter.attach(message);
		
		twitter.publish(1000, "你好中国");
		twitter.publish(10001, "中国你好");
	}
}
