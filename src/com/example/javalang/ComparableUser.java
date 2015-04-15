package com.example.javalang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableUser implements Comparable<ComparableUser> {

	public static void main(String[] args) {
		List<ComparableUser> list = new ArrayList<ComparableUser>();

		ComparableUser cj = new ComparableUser(27, "八两俊");
		ComparableUser hhh = new ComparableUser(29, "hhh");
		ComparableUser wl = new ComparableUser(25, "猪古丽");
		list.add(cj);
		list.add(hhh);
		list.add(wl);
		Collections.sort(list);
		for (ComparableUser user : list)
			System.out.println(user);
	}

	private int age;
	private String name;

	public ComparableUser(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(ComparableUser user) {
		return this.age - user.age;
	}

	@Override
	public String toString() {
		return "ComparableUser [age=" + age + ", name=" + name + "]";
	}
}
