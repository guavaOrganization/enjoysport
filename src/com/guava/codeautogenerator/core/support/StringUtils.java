package com.guava.codeautogenerator.core.support;

public class StringUtils {
	public static final String EMPTY = "";
	public static final String POINT_SYMBOL = ".";
	public static final String PACKAGE_SERVICE = "service";
	public static final String PACKAGE_INTERFACES = "interfaces";
	public static final String PACKAGE_CONTROLLER = "controller";
	public static final String CONTROLLER_CLASS_SUFFIX_NAME = "Controller";
	public static final String SERVICE_CLASS_SUFFIX_NAME = "Service";
	public static final String INTERFACE_PREFIX = "I";// 接口前缀

	public static final String KEY_MODULE_NAME = "moduleName";
	public static final String KEY_SUB_MODULE_NAME = "subModuleName";
	public static final String KEY_CLASS_NAME = "className";

	public static boolean isBlank(String str) {
		return null == str || str.trim().length() == 0;
	}
}
