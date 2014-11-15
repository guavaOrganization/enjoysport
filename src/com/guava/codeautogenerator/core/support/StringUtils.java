package com.guava.codeautogenerator.core.support;

public class StringUtils {
	public static final String EMPTY = "";

	public static boolean isBlank(String str) {
		return null == str || str.trim().length() == 0;
	}
}
