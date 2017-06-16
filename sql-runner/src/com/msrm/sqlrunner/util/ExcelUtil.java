package com.msrm.sqlrunner.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static Workbook createWorkbook(String file) {
		if (file.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook();
		} else if (file.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook();
		} else {
			return null;
		}
	}

	public static void writeToFile(String fileName, Workbook wb) {
		try (FileOutputStream fos = new FileOutputStream(fileName)) {
			wb.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}