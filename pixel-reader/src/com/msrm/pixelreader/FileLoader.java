package com.msrm.pixelreader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLoader {

	private char[][] data;

	public FileLoader(String screenFile) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(screenFile));
			this.data = new char[lines.size()][];
			for (int i = 0; i < lines.size(); i++) {
				this.data[i] = lines.get(i).toCharArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enterValue(String value, int[] positions) {
		if (value != null && positions != null) {
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				this.data[positions[0] - 1][positions[1] - 1 + i] = chars[i];
			}
		}
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();
		for (char[] cs : data) {
			sb.append(cs);
			sb.append("\n");
		}
		return sb.toString();
	}

}
