package com.msrm.dbutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileUtil {

	public static File newFileName(String path) {
		try {
			//@formatter:off
			String file = Files.walk(Paths.get(path), FileVisitOption.FOLLOW_LINKS)
					.map(Path::toFile)
					.filter(f -> f.getName().endsWith(".xls") && f.getName().startsWith("report-"))
					.map(File::getName)
					.mapToInt(f -> Integer.parseInt(f.substring("report-".length(), f.indexOf(".xls"))))
					.sorted()
					.findFirst()
					.get();
			
			
			Files.walk(Paths.get(path), FileVisitOption.FOLLOW_LINKS)
			.map(Path::toFile)
			.filter(f -> f.getName().endsWith(".xls") && f.getName().startsWith("report-"))
			.map(File::getName)
			.sorted()
			.forEach(System.out::println);
			//@formatter:on
			int n = Integer.parseInt(file.substring("report-".length(), file.indexOf(".xls")));
			return new File("report-" + ++n + ".xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		File newFile = newFileName("/Users/srirammuthaiah/reports");
		System.out.println();
		System.out.println(newFile);
	}
}
