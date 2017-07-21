package com.msrm.jdk8.features;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HowLambdaWorks {

	public static void main(String[] args) throws IOException {

		// Example 1
		JFrame frame = new JFrame("Application");
		frame.setVisible(true);
		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JButton saveButton = new JButton("Save");
		JButton openButton = new JButton("Open");
		panel.add(saveButton);
		panel.add(openButton);
		frame.getContentPane().add(panel);

		// object oriented way of thinking
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save button clicked");
			}
		});

		// functional way of thinking
		saveButton.addActionListener(e -> System.out.println("Save button clicked"));
		openButton.addActionListener(System.out::println);

		// Example 2
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread is running");
			}
		});
		t.start();

		new Thread(() -> System.out.println("Thread is running")).start();

		// Throwing exception in lambda expression
		Callable<Void> runner = () -> {
			System.out.println("Welcome to Lamdba");
			Thread.sleep(1000);
			return null;
		};

		Executors.newFixedThreadPool(5).submit(runner);
	}

}
