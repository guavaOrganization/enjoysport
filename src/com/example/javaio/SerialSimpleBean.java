package com.example.javaio;

import java.io.Serializable;

public class SerialSimpleBean implements Serializable {
	private static final long serialVersionUID = 3590896709264704267L;
	private int id;
	private String text;

	public SerialSimpleBean() {
		System.out.println("无参构造器...");
	}

	public SerialSimpleBean(int id, String text) {
		System.out.println("全参构造器...");
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "SerialSimpleBean [id=" + id + ", text=" + text + "]";
	}
}
