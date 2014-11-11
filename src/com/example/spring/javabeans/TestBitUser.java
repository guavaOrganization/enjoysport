package com.example.spring.javabeans;

import java.io.Serializable;

public class TestBitUser implements Serializable {
	private static final long serialVersionUID = 4927718208411935266L;
	private int id;
	private String Name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "TestBitUser [id=" + id + ", Name=" + Name + "]";
	}
}
