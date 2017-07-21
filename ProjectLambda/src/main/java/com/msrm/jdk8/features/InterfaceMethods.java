package com.msrm.jdk8.features;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class InterfaceMethods {

	public static void main(String[] args) {
		Computable<Integer> c = i -> i + 4;
		System.out.println(c.checkIfEven(5)); // checkIfEven is default method in Computable interface
		System.out.println(c.checkIfEven(4));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};
		List<Integer> ints = Arrays.asList(5, 3, 1, 8, 2, 0, 19, 23, 2, 7);
		System.out.println("Ints : " + ints);
		ints.sort(comparator);
		System.out.println("Ints : " + ints);
		
		//How does work in Lambda style?
		
		//How does work in Method reference?
		
	}

}
