package com.example.designpattern.proxy;

public interface Connection {
	Object get();

	void set(Object x);

	void release();
}
