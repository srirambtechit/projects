package com.msrm.jdk7.features;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TryWithResource {

	public static void main(String[] args) {
		// no need to handle closing of the resource
		// since JDK 7, it will be handled by JVM
		try (FileInputStream fis = new FileInputStream("/path/to/file")) {
			int c = fis.read();
			while (c != -1) {
				System.out.println((char) c);
				c = fis.read();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
