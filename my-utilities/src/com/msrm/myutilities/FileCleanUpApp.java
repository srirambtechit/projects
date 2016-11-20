package com.msrm.myutilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileCleanUpApp {

	public static void main(String[] args) {
		FileCleanUpApp app = new FileCleanUpApp();
		String rootPath = "/Users/srirammuthaiah/Documents/Tutorials/_downloading";
		app.deleteFileNameTextFiles(rootPath);
	}

	public void deleteFileNameTextFiles(String rootPath) {
		Stream.of(new File(rootPath).listFiles()).forEach(f -> {
			if (f.isDirectory()) {
				File file = new File(f.getAbsolutePath() + File.separator + "file_names.txt");
				System.out.println(file);
				try {
					Files.deleteIfExists(Paths.get(file.toURI()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
