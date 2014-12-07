package com.example.beetl.test;

import java.text.SimpleDateFormat;

import org.beetl.core.Format;

public class BeetlCustomFormat implements Format {
	@Override
	public Object format(Object paramObject, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(paramObject);
	}
}
