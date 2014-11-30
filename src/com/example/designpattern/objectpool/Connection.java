package com.example.designpattern.objectpool;

public interface Connection {// 目标对象
	Object get();

	void set(Object object);
}
