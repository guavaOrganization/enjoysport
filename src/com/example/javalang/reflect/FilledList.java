package com.example.javalang.reflect;

import java.util.ArrayList;
import java.util.List;

public class FilledList<T> {
	private Class<T> type;

	public FilledList(Class<T> type) {
		this.type = type;
	}

	public List<T> create(int nElements) {
		List<T> result = new ArrayList<T>();
		try {
			for (int i = 0; i < nElements; i++) {
				result.add(type.newInstance());
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		FilledList<CountedInteger> f = new FilledList<CountedInteger>(CountedInteger.class);
		System.out.println(f.create(15));
	}
}
