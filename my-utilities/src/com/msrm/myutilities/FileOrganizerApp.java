package com.msrm.myutilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FileOrganizerApp {

	private static int uniqId = 0;
	private final static String dsStore = ".DS_Store";
	private final static String exerciseFile = "exercise";

	private static class FileData {
		int uniqId;
		int linkedInId;
		File tutorialFolder;
		int directoryCount;
		int fileCount;
		List<String> files;
		Map<String, String> dirs;
		String exerciseDir;
		String exerciseFile;

		public FileData() {
			files = new ArrayList<>();
			dirs = new HashMap<>();
		}

		@Override
		public String toString() {
			return "FileData [uniqId=" + uniqId + ", linkedInId=" + linkedInId + ", tutorialFolder="
					+ tutorialFolder + ", directoryCount=" + directoryCount + ", fileCount="
					+ fileCount + ", files=" + files + ", dirs=" + dirs + ", exerciseDir="
					+ exerciseDir + ", exerciseFile=" + exerciseFile + "]";
		}

	}

	private static Map<Integer, FileData> map = new HashMap<>();

	public static void main(String[] args) {
		FileOrganizerApp app = new FileOrganizerApp();
		app.initialize("/Users/srirammuthaiah/Documents/Tutorials");
		app.collectFileData();
		app.moveFiles();
	}

	private void moveFiles() {
		if (map != null) {

			Map<String, String> ft = getFileTable();

			System.out.println(ft);

			for (Entry<Integer, FileData> entry : map.entrySet()) {
				FileData fd = entry.getValue();
				for (int i = 0; i < fd.directoryCount; i++) {
					String dirNumber = String.format("%02d_", i);
					String path = fd.tutorialFolder.getPath();
					String fileKey = fd.linkedInId + "_" + dirNumber;

					for (Entry<String, String> e : ft.entrySet()) {
						String fileName = e.getValue();
						String source = path + File.separator + fileName;

						String target = path + File.separator + fd.dirs.get(dirNumber)
								+ File.separator + fileName;
						System.out.println(dirNumber);
						System.out.println("Moving from " + source + " to " + target);
					}

					System.out.println(dirNumber);
					System.out.println(fd.dirs);

					// try {

					// Files.move(Paths.get(path + File.pathSeparator +
					// dirNumber),
					// Paths.get(path + File.pathSeparator + dirNumber),
					// StandardCopyOption.ATOMIC_MOVE);
					// } catch (IOException e) {
					// e.printStackTrace();
					// }

				}
			}
		}
	}

	private Map<String, String> getFileTable() {
		Map<String, String> fileMap = new HashMap<>();
		for (Entry<Integer, FileData> entry : map.entrySet()) {
			FileData fd = entry.getValue();
			List<String> fileList = fd.files;
			for (int i = 0; i < fd.directoryCount; i++) {
				String chapterNumber = String.format("%02d_", i);
				for (int j = 0; j < 20; j++) {
					String topicNumber = String.format("%02d_", j);
					String fileName = fd.linkedInId + "_" + chapterNumber + topicNumber;
					for (String name : fileList) {
						if (name.contains(fileName)) {
							fileMap.put(fileName, name);
						}
					}

				}
			}
		}
		return fileMap;
	}

	public void initialize(String dataPath) {
		try {
			File rootPath = new File(dataPath);
			File[] files = rootPath.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					FileData fd = new FileData();
					fd.uniqId = ++uniqId;
					fd.tutorialFolder = file.getCanonicalFile();
					map.put(uniqId, fd);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void collectFileData() {
		Set<Integer> keys = map.keySet();
		Iterator<Integer> itr = keys.iterator();
		while (itr.hasNext()) {
			Integer uniqId = itr.next();
			FileData fileData = map.get(uniqId);
			analyseFiles(fileData.tutorialFolder, fileData);
			fileData.directoryCount = fileData.dirs.size();
			fileData.fileCount = fileData.files.size();
		}
	}

	private void analyseFiles(File file, FileData fileData) {
		if (file == null)
			return;
		if (file.isFile() && !dsStore.equalsIgnoreCase(file.getName())) {
			fileData.files.add(file.getName());
			if (fileData.linkedInId == 0) {
				String name = file.getName();
				try {
					if (name.indexOf("_") > 0) {
						fileData.linkedInId = Integer
								.parseInt(name.substring(0, name.indexOf("_")));
					}
				} catch (NumberFormatException e) {
				}
			}
			if (file.getName().contains("zip") || file.getName().contains(".7z")) {
				fileData.exerciseFile = file.getName();
			}
		}
		if (file.isDirectory()) {
			String name = file.getName();
			String dirId = "";
			if (file.getName().toLowerCase().contains(exerciseFile)) {
				fileData.exerciseDir = file.getName();
				fileData.dirs.put("Ex_Dir", file.getName());
			} else if (name.indexOf("_") > 0) {
				dirId = name.substring(0, name.indexOf("_") + 1);
				fileData.dirs.put(dirId, file.getName());
			}
			for (File f : file.listFiles()) {
				analyseFiles(f, fileData);
			}
		}
	}

}
