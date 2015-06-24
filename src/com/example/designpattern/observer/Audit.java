package com.example.designpattern.observer;

public class Audit extends Observer {
	private Twitter twitter;

	public Audit(Twitter twitter) {
		this.twitter = twitter;
	}

	@Override
	public void update() {
		int twitterId = twitter.getTwitterId();
		int userId = twitter.getUserId();
		System.out.println("Audit~~twitterId=" + twitterId + ",userId=" + userId);
	}
}
