package com.example.javalang.cloneable;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 	此类实现了Cloneable接口，以指示Object.clone()方法可以合法地对该类实例进行按字段复制。
 * 如果在没有实现Cloneable接口的实例上调用Object的 clone方法，则会导致抛出 CloneNotSupportedException 异常。
 * 按照惯例，实现此接口的类应该使用公共方法重写 Object.clone（它是受保护的）。请参阅 Object.clone()，以获得有关重写此方法的详细信息。
 * 注意，此接口不包含clone方法。因此，因为某个对象实现了此接口就克隆它是不可能的。即使 【clone方法是反射性调用的，也无法保证它将获得成功】。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class CloneableUser implements Cloneable {
	private int id;
	private String name;

	private CloneableUserAddress address;

	public CloneableUser(int id, String name, CloneableUserAddress address) {
		System.out.println("CloneableUser(int id, String name)");
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CloneableUserAddress getAddress() {
		return address;
	}

	public void setAddress(CloneableUserAddress address) {
		this.address = address;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	@Override
	public String toString() {
		return "CloneableUser [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
}
