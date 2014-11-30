package com.example.designpattern.simplifying;

/**
 * 
 * @Copyright (c) 2011-2014 guava(番石榴工作室)
 * <p>
 * 最普通的方法就是通过信使（messenger），它简单的将信息打包到一个用于传送的对象，而不是将这些信息碎片单独传送
 * </p>
 * 
 * <p>
 * 因为 messenger 只是用来传送数据，它所传送的数据通常声明为公有的，以便于存取。但是，你可以根据自己的需要把它们声明成私有的。
 * </p>
 * 
 * @author 八两俊 
 * @since
 */
public class Point {// A Messager
	private int x, y, z;

	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
		this.z = point.z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
