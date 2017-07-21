package com.msrm.jdk7.features;

public class NumberUnderscore {

	public static void main(String[] args) {
		int thousand = 1000;
		int million = 1000_000;
		int billion = 1000_000_000;
		long trillion = 1000_000_000_000L;

		System.out.println("Thousand : " + thousand);
		System.out.println("Million : " + million);
		System.out.println("Billion : " + billion);
		System.out.println("Trillion : " + trillion);
	}

}
