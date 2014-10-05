package com.example.beetl.formats;

import java.text.SimpleDateFormat;

import org.beetl.core.Format;

public class GuavaBeetlDateFormat implements Format {
	@Override
	public Object format(Object paramObject, String datePattern) {
		if(null == datePattern || "".equals(datePattern.trim())){
			datePattern = "yyyy-MM-dd HH:mm:ss";	
		}
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		return format.format(paramObject);
	}
}
