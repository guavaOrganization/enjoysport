package com.example.designpattern.observer;

public abstract class Observer { // 观察者
	/**
	 * “观察者”类提供给“发布者”类的接口,此接口的实现关键点在于并没有参数,这样达到通用性
	 */
	public abstract void update();
}
