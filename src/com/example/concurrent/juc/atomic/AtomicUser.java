package com.example.concurrent.juc.atomic;

public class AtomicUser {
	volatile String hobby;
	
	private int id;
	private String name;

	public AtomicUser(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AtomicUser [id=" + id + ", name=" + name + "]";
	}
}
