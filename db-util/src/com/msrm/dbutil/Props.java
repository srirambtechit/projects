package com.msrm.dbutil;

import java.io.IOException;
import java.util.Properties;

public class Props {

	private static final Props INSTANCE = new Props();

	private Properties prop = new Properties();

	private Props() {
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String value(String key) {
		System.out.println("Key : " + key + "; Value : " + INSTANCE.prop.getProperty(key));
		return INSTANCE.prop.getProperty(key);
	}

}
