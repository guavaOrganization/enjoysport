package com.example.designpattern.observer;

public class Message extends Observer {
	private Twitter twitter;

	public Message(Twitter twitter) {
		this.twitter = twitter;
	}

	@Override
	public void update() {
		int twitterId = twitter.getTwitterId();
		int userId = twitter.getUserId();
		System.out.println("Message~~twitterId=" + twitterId + ",userId=" + userId);
	}
}
