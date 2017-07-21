package com.msrm.concurrency;

import java.util.concurrent.TimeUnit;

public class NumberGenerator implements Runnable {

	private static int count;

	private int taskId;

	public NumberGenerator() {
		this.taskId = ++count;
	}

	public int getId() {
		return taskId;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				int newNumber = (int) (Math.random() * 100);
				System.out.println("<Task-" + taskId + "> is generating " + newNumber);
				TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
