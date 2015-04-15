package com.example.javalang.cloneable;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	浅拷贝
 * 	通过实现Cloneable接口，实现父类Object的clone方法实现浅拷贝
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class ShallowTest {
	public static void main(String[] args) throws CloneNotSupportedException {
		CloneableUser user = new CloneableUser(1, "陈俊", new CloneableUserAddress("广州"));
		CloneableUser cloneableUser = (CloneableUser) user.clone();
		System.out.println(user == cloneableUser); // false
		System.out.println(user.equals(cloneableUser)); // false
		
		System.out.println(cloneableUser);
		cloneableUser.setName("吴丽");
		System.out.println(cloneableUser);
		System.out.println(user);
		
		// 浅拷贝
		// 对于要克隆的对象，对于其基本数据类型的属性，复制一份给新产生的对象，对于非基本数据类型的属性，仅仅复制一份引用给新产生的对象，
		// 即新产生的对象和原始对象中的非基本数据类型的属性都指向的是同一个对象
		System.out.println(user.getAddress() == cloneableUser.getAddress());
		user.getAddress().setAddress("广州市天河区");
		System.out.println(cloneableUser.getAddress());
	}
}