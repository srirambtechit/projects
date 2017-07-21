package com.msrm.jdk8.features;

import java.util.function.Consumer;

public class MethodReference {

	public static void main(String[] args) {
		Consumer<String> printer = System.out::println;
		printer.accept("Hello, World");
	}

}
