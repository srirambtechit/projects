package com.ctm.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ctm.common.ErrorConstants;
import com.ctm.exceptions.DataException;

public class FileUtils {

    private FileUtils() {
    }

    public static List<String> readContent(String fileName) throws DataException {
	List<String> contents = new ArrayList<>();

	try (FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader)) {
	    String line = bufferedReader.readLine();
	    while (line != null) {
		contents.add(line);
		line = bufferedReader.readLine();
	    }
	} catch (IOException e) {
	    if (e instanceof FileNotFoundException) {
		throw new DataException(fileName + " " + ErrorConstants.ERR_FILE_01, e);
	    }
	    throw new DataException(ErrorConstants.ERR_FILE_02, e);
	}
	return contents;
    }

    public static void main(String[] args) {
	try {
	    List<String> contents = FileUtils.readContent("D:\\input.txt");
	    System.out.println(contents);
	} catch (DataException e) {
	    System.out.println(e.getMessage());
	}
    }

}
