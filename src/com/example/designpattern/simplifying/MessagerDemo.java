package com.example.designpattern.simplifying;

import org.junit.Test;

public class MessagerDemo {
	@Test
	public void testMessager() {
		Point point = new Point(1, 2, 3);
		Point p = Space.translate(point, new Vector(1, 5));
		System.out.println(p);
	}
}
