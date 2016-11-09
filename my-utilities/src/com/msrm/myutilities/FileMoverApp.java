package com.msrm.myutilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class FileMoverApp {

	public static void main(String[] args) {
		FileMoverApp app = new FileMoverApp();
		String rootPath = "/Users/srirammuthaiah/Documents/Tutorials/_downloading";
		List<File> tutorials = app.gatherAllTutorials(rootPath);

		 app.printTutorialList(tutorials);

		for (File tutorialFolder : tutorials) {
			app.analyzeThenMoveFiles(tutorialFolder);
		}
		
	}

	public void printTutorialList(List<File> tutorials) {
		
		// Java 8 - Lambda way
		tutorials.stream().filter(f -> {
			Stream<File> stream = Stream.of(f.listFiles((dir, name) -> {
				return name.endsWith(".mp4") || name.endsWith(".zip") || name.endsWith(".7z");
			}));
			return stream.toArray().length > 0;
		}).forEach(System.out::println);

		System.out.println();

		// traditional Java way
		Set<String> tuts = new HashSet<>();
		for (File tf : tutorials) {
			File[] zipOrVideoFiles = tf.listFiles((dir, name) -> {
				return name.endsWith(".mp4") || name.endsWith(".zip") || name.endsWith(".7z");
			});
			if (zipOrVideoFiles.length > 0) {
				tuts.add(tf.getName());
				System.out.println(tf);
			}
		}

	}

	public void analyzeThenMoveFiles(File tutorialFolder) {

		if (tutorialFolder != null) {

			Map<String, File> folderMap = new HashMap<>();

			File[] zipOrVideoFiles = tutorialFolder.listFiles((dir, name) -> {
				return name.endsWith(".mp4") || name.endsWith(".zip") || name.endsWith(".7z");
			});

			if (zipOrVideoFiles != null && zipOrVideoFiles.length > 0) {

				File[] chapterFolders = tutorialFolder.listFiles(path -> {
					return path.isDirectory();
				});
				for (File file : chapterFolders) {
					String name = file.getName();
					String folderNumber = name.substring(0, name.indexOf('_'));
					if (folderNumber != null && !folderNumber.isEmpty()
							&& folderNumber.equalsIgnoreCase("exercise")) {
						folderMap.put("Ex", file);
					} else {
						folderMap.put(folderNumber, file);
					}
				}

				for (File zipOrVideoFile : zipOrVideoFiles) {
					String name = zipOrVideoFile.getName();
					int startIndex = name.indexOf('_') + 1;

					String source = zipOrVideoFile.getAbsolutePath();
					File chapterFolder = null;

					if (name.startsWith("Ex")) {
						// System.out.println(name);
						chapterFolder = folderMap.get("Ex");
					} else if (name != null && startIndex > 0 && startIndex + 2 < name.length()) {
						String folderNumber = name.substring(startIndex, startIndex + 2);
						chapterFolder = folderMap.get(folderNumber);
					}

					if (chapterFolder != null) {
						String target = chapterFolder.getAbsolutePath() + File.separator
								+ zipOrVideoFile.getName();

						try {
							Files.move(Paths.get(source), Paths.get(target),
									StandardCopyOption.ATOMIC_MOVE);
						} catch (IOException e) {
							System.out.println("Error: Moving " + source + " -> " + target);
						}

//						System.out.println(source + " -> " + target);
					}

				}

			}
		}

	}

	public List<File> gatherAllTutorials(String rootPath) {
		List<File> tutorials = new ArrayList<>();
		File[] files = new File(rootPath).listFiles();
		for (File file : files) {
			if (file.isDirectory())
				tutorials.add(file);
		}
		return tutorials;
	}

}
