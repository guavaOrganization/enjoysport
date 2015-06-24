package com.example.designpattern.observer;

public class Twitter extends Subject {
	private int twitterId;
	private int userId;
	private int id = 0;

	public int getTwitterId() {
		return twitterId;
	}

	public int getUserId() {
		return userId;
	}

	public boolean publish(int userId, String content) {
		this.userId = userId;
		this.twitterId = save(content);
		notifyObservers();
		return true;
	}

	private int save(String content) {
		System.out.println(content);
		return ++id;
	}
}
