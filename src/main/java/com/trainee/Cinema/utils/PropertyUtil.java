package com.trainee.Cinema.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	public static final String PROPERTY_FILE_NAME = "properties.properties";

	public static String getProperty(String key) {
		Properties properties = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream resourceStream = loader.getResourceAsStream("properties.properties");
			properties.load(resourceStream);
			return properties.getProperty(key);
		} catch (Exception e) {
			throw new RuntimeException("Can not load properties from " + PROPERTY_FILE_NAME);
		}
	}
}
