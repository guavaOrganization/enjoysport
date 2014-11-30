package com.example.thread;

public class Toast {
	public enum Status {
		DRY, BUTTERED, JAMMED
	}

	private Status status = Status.DRY;

	private final int id;

	public Toast(int idn) {
		this.id = idn;
	}

	public void butter() {
		this.status = Status.BUTTERED;
	}

	public void jam() {
		this.status = Status.JAMMED;
	}

	public Status getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Toast " + id + ": " + status;
	}
}
