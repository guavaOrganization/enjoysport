package com.example.designpattern.simplifying;

public class Space {
	public static Point translate(Point p, Vector v) {
		Point point = new Point(p);
		point.setX(p.getX());
		point.setY(p.getY());
		point.setZ(p.getZ());
		return point;
	}
}
