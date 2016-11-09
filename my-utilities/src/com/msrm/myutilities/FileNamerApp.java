package com.msrm.myutilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileNamerApp {

	public static void main(String[] args) {
		FileNamerApp app = new FileNamerApp();

		String downloadPath = "/Users/srirammuthaiah/Documents/Tutorials/_downloading";
		String dataFile = "file_names.txt";

		List<String> eligiblePaths = app.findEligibleFolder(downloadPath, dataFile);
		for (String path : eligiblePaths) {
			System.out.println("videoPath : " + path);
			System.out.println("dataFile : " + path + File.separator + dataFile);
			Map<String, String> newFileNames = app.loadFileNames(path + File.separator + dataFile);
			app.formFileNames(newFileNames);
			app.applyOnFiles(path, newFileNames);
		}
	}

	public List<String> findEligibleFolder(String downloadPath, String dataFile) {
		return Stream.of(new File(downloadPath).listFiles(f -> {
			if (f.isDirectory()) {
				File dataFileObject = new File(
						downloadPath + File.separator + f.getName() + File.separator + dataFile);
				return f.listFiles(f1 -> f1.equals(dataFileObject)).length > 0;
			}
			return false;
		})).map(f -> f.getAbsolutePath()).collect(Collectors.toList());
	}

	public List<String> findEligibleFolder0(String downloadPath, String dataFile) {
		List<File> list = Stream.of(new File(downloadPath).listFiles(f -> f.isDirectory()))
				.collect(Collectors.toList());
		List<String> folders = new ArrayList<>();

		for (File file : list) {
			File dataFileObject = new File(
					downloadPath + File.separator + file.getName() + File.separator + dataFile);
			if (file.listFiles(f -> f.equals(dataFileObject)).length > 0) {
				System.out.println(file);
			}
		}
		return folders;
	}

	public static void main0(String[] args) {
		FileNamerApp app = new FileNamerApp();
		String dataFile = "/Users/srirammuthaiah/Documents/Tutorials/_downloading/EmberJS Essential Training/file_names.txt";
		String videoFilePath = "/Users/srirammuthaiah/Documents/Tutorials/_downloading/EmberJS Essential Training";
		Map<String, String> newFileNames = app.loadFileNames(dataFile);
		app.formFileNames(newFileNames);
		app.applyOnFiles(videoFilePath, newFileNames);
	}

	public void applyOnFiles(String videoFilePath, Map<String, String> newFileNames) {
		File videoFileFolder = new File(videoFilePath);

		Stream.of(videoFileFolder.listFiles((dir, name) -> name.endsWith(".mp4")))
				.forEach(source -> {
					String key = source.getName().substring(0, source.getName().indexOf(".mp4"));
					String newFileName = newFileNames.get(key);
					if (newFileName != null) {
						File target = new File(source.getParent() + File.separator + newFileName);
						try {
							Files.move(Paths.get(source.toURI()), Paths.get(target.toURI()),
									StandardCopyOption.ATOMIC_MOVE);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println(source + " -> " + target);
					}
				});
	}

	public void formFileNames(Map<String, String> fileNames) {
		fileNames.forEach((presentName, futureName) -> {
			int qIndex = futureName.indexOf('?');
			if (qIndex > -1) {
				fileNames.put(presentName, futureName.substring(0, qIndex));
			}
		});
	}

	public Map<String, String> loadFileNames(String dataFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(dataFile));) {
			return reader.lines().map(line -> line.split(","))
					.collect(Collectors.toMap(s -> s[0], s -> s[1]));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
