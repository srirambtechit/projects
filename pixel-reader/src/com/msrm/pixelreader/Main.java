package com.msrm.pixelreader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		String path = "/Users/srirammuthaiah/Coding/workspace/pixel-reader/";
		String dataFile = path + "data.txt";
		String configFile = path + "config.txt";
		String screenFile = path + "Screen.txt";
		String screenOutputFile = path + "FilledScreen.txt";

		FileLoader loader = new FileLoader(screenFile);

		System.out.println("Data loaded into System...");

		ExternalFileReader dataEFR = new ExternalFileReader(dataFile);
		ExternalFileReader configEFR = new ExternalFileReader(configFile);

		System.out.println("External configuration loaded...");

		for (String key : dataEFR.getKeys()) {
			String value = dataEFR.getValue(key);
			int[] positions = configEFR.getPositions(key);
			loader.enterValue(value, positions);
		}

		System.out.println("Processing information...");
		try {
			Files.write(Paths.get(screenOutputFile), loader.getContent().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("It's done!!!");

	}

}
