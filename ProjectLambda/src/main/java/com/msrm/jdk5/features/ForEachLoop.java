package com.msrm.jdk5.features;

public class ForEachLoop {
	public static void main(String[] args) {
		Integer[] ints = {1, 3, 4, 5, 6, 7, 9};
		for (Integer num : ints) {
			System.out.println(num);
		}
	}
}
