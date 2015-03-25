package com.example.javaio;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 4700008086543615434L;

	private String name;
	private Integer age;
	private Integer score;
	private Gender gender;

	private static class InstanceHolder {
		private static final Person instance = new Person("陈俊", 27, 100, Gender.MALE);
	}
	
	public static Person getInstance(){
		return InstanceHolder.instance;
	}

	private Person() {
	}
	
	private Person(String name, Integer age, Integer score, Gender gender) {
		this.name = name;
		this.age = age;
		this.score = score;
		this.gender = gender;
	}
	
	private Object readResolve() throws ObjectStreamException {  
        return InstanceHolder.instance;  
    }  

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", score=" + score + ", gender=" + gender + "]";
	}
}
