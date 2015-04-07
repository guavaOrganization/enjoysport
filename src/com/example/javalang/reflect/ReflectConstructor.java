package com.example.javalang.reflect;

public class ReflectConstructor {
	private int i;
	private int j;
	private int k;
	private int q;

	public ReflectConstructor(int i) {
		this.i = i;
	}

	protected ReflectConstructor(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public ReflectConstructor() {
	}

	ReflectConstructor(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}

	private ReflectConstructor(int i, int j, int k, int q) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.q = q;
	}
}
