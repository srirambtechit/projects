package com.msrm.concurrency;

public class TraditionalThreading {

	public static void main(String[] args) {
		System.out.println("Main thread starts execution...");
		new Thread(new NumberGenerator()).start();
		new Thread(new NumberGenerator()).start();
		new Thread(new NumberGenerator()).start();
		System.out.println("Main thread ends execution...");
	}

}
