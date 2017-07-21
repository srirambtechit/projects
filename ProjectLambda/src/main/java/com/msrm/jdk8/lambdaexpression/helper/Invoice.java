package com.msrm.jdk8.lambdaexpression.helper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

	private String name;
	private double amount;
	private Customer customer;

	public Invoice(String name, double amount, Customer customer) {
		super();
		this.name = name;
		this.amount = amount;
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Invoice [name=" + name + ", amount=" + amount + ", customer=" + customer + "]";
	}

	private static final DecimalFormat df = new DecimalFormat("####0.00");

	public static List<Invoice> invoices(int count) {
		List<Invoice> invoices = new ArrayList<>();
		Customer[] customers = { Customer.AMAZON, Customer.AZURE, Customer.IBM, Customer.ORACLE };
		String[] names = { "Sriram", "Prabhu", "Shankar", "Suresh", "Sudha", "Sanjay", "Saji", "Kannan", "Meenakshi",
				"Kumar", "Dinesh", "Siva", "Babu", "Jill", "Mike" };

		for (int i = 0; i < count; i++) {
			invoices.add(new Invoice(names[(int) (names.length * Math.random())],
					Double.parseDouble(df.format(Math.random() * 10000)),
					customers[(int) (customers.length * Math.random())]));
		}

		return invoices;
	}

}
