package com.guava.codeautogenerator.support;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtils {
	public static Properties loadProperties(String path) throws IOException{
		Properties props = new Properties();
		props.load(PropertiesUtils.class.getResourceAsStream(path));
		return props;
	}
	
	public static void mergeProperties(Properties properties, Properties customProperties, Set<String> ignorePropertieKeys) {
		if(null == properties || null == customProperties)
			return;
		Set<String> customKeys = customProperties.stringPropertyNames();
		Iterator<String> customIte =  customKeys.iterator();
		while (customIte.hasNext()) {
			String key = (String) customIte.next();
			if(null != ignorePropertieKeys && ignorePropertieKeys.contains(key))
				continue;
			properties.put(key, customProperties.getProperty(key));
		}
	}
	
	public static boolean getProperty(Properties properties, String key, boolean defaultValue) {
		if (null == properties || null == key)
			return defaultValue;
		try {
			boolean ret = Boolean.parseBoolean(properties.getProperty(key));
			return ret;
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static String getProperty(Properties properties, String key, String defaultVale) {
		if (null == properties || null == key)
			return defaultVale;
		String value = properties.getProperty(key);
		if (null == value && null != defaultVale)
			return defaultVale;
		return value;
	}
	
	public static List<String> getProperty(Properties properties, String key, List<String> defaultValue, String separator) {
		if (null == properties || null == key)
			return defaultValue;
		String value =getProperty(properties, key, "");
		return Arrays.asList(value.split(separator));
	}
}
