package com.msrm.jdk7.features;

public class SwitchString {

	public static void main(String[] args) {
		if (args.length > 0) {
			String option = args[0];
			String value = null;
			if (args.length > 1) {
				value = args[1];
			}
			switch (option) {
				case "-print" :
					printHelpMessage();
					break;
				case "-quit" :
					System.out.println("Bye!!!");
					System.exit(0);
					break;
				case "-greet" :
					System.out.println("Hello, " + value);
			}
		} else {
			printHelpMessage();
		}
	}

	private static void printHelpMessage() {
		StringBuilder messageBuilder = new StringBuilder();
		//@formatter:off
		messageBuilder
			.append("java SwitchString <option> <value>").append("\n")
			.append(" -print --- this message").append("\n")
			.append(" -greet --- Greetings").append("\n")
			.append(" -quit  --- Exit application");
		//@formatter:on
		System.out.println(messageBuilder);
	}

}
