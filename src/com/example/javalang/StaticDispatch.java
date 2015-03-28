package com.example.javalang;

/**
 * 
 * <p>
 * 方法静态分派演示
 * </p>
 * 
 */
public class StaticDispatch {
	static abstract class Human {

	}

	static class Man extends Human {

	}

	static class Woman extends Human {

	}

	public void sayHello(Human guy) {
		System.out.println("hello,guy");
	}

	public void sayHello(Man guy) {
		System.out.println("hello,gentleman");
	}

	public void sayHello(Woman guy) {
		System.out.println("hello,lady");
	}

	public void growing(Biont biont) {
		System.out.println("biont growing");
	}

	public void growing(Animal biont) {
		System.out.println("animal growing");
	}

	public void growing(Plant biont) {
		System.out.println("plant growing");
	}

	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		StaticDispatch dispatch = new StaticDispatch();
		dispatch.sayHello(man);
		dispatch.sayHello(woman);
		dispatch.sayHello(new Man());
		dispatch.sayHello(new Woman());
		System.out.println("=======================");
		Biont animal = new Animal();
		Biont plant = new Plant();
		dispatch.growing(animal);
		dispatch.growing(plant);
	}
}
