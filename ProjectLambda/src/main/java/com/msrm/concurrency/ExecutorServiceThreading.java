package com.msrm.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceThreading {

	public static void main(String[] args) {
		System.out.println("Main thread starts execution...");
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(new NumberGenerator());
		executorService.submit(new NumberGenerator());
		executorService.submit(new NumberGenerator());
		executorService.shutdown();
		
		System.out.println("Main thread ends execution...");
	}

}