package com.msrm.jdk7.features;

public class BinaryLiterals {

	public static void main(String[] args) {
		byte b1 = 0b111111;
		byte b2 = 0b101011;
		int result = 0;
		if (b1 > b2)
			result = b1 - b2;
		else
			result = b2 - b1;
		System.out.println(result);
	}

}
