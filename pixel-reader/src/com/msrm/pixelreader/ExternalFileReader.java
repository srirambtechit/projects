package com.msrm.pixelreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExternalFileReader {

	private Properties props;

	public ExternalFileReader(String file) {
		props = new Properties();
		try {
			props.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getPositions(String key) {
		String[] strs = getValue(key).split(",");
		int[] ints = new int[strs.length];
		for (int i = 0; i < strs.length; i++) {
			ints[i] = Integer.parseInt(strs[i]);
		}
		return ints;
	}

	public String[] getKeys() {
		return props.keySet().toArray(new String[0]);
	}

	public String getValue(String key) {
		return props.getProperty(key);
	}

}
