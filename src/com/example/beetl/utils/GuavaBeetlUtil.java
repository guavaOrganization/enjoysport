package com.example.beetl.utils;

import java.io.IOException;

import org.beetl.core.Context;

public class GuavaBeetlUtil {
	// 判断是否为空字符串
	public boolean isBlankString(String str,Context context) throws IOException {
		if (null == str || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
}
