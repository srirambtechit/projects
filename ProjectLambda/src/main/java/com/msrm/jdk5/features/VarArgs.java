package com.msrm.jdk5.features;

import java.util.StringJoiner;

public class VarArgs {

	public static void main(String[] args) {
		String name = "Sriram";
		String address = "Chennai";
		String office = "One India Bulls Park";

		VarArgs app = new VarArgs();
		String finalString = app.joiner(name, address, office);

		System.out.println(finalString);
	}

	public String joiner(String... value) {
		StringJoiner joiner = new StringJoiner(",");
		for (String string : value) {
			joiner.add(string);
		}
		return joiner.toString();
	}

}
