package com.msrm.jdk8.lambdaexpression.helper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Employee {
	public enum Sex {
		MALE, FEMALE
	}
	private String name;
	private int id;
	private double salary;
	private Sex sex;
	private String city;

	public Employee(String name, int id, double salary, Sex sex, String city) {
		super();
		this.name = name;
		this.id = id;
		this.salary = salary;
		this.sex = sex;
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Employee [name=" + name + ", id=" + id + ", salary=" + salary + ", sex=" + sex + ", city=" + city + "]";
	}

	private static final DecimalFormat df = new DecimalFormat("####0.00");

	public static List<Employee> employees(int count) {
		List<Employee> result = new ArrayList<>();
		Sex[] sex = {Sex.MALE, Sex.FEMALE};
		String[] names = {"Sriram", "Prabhu", "Shankar", "Suresh", "Sudha", "Sanjay", "Saji", "Kannan", "Meenakshi", "Kumar", "Dinesh", "Siva", "Babu", "Jill", "Mike"};
		String[] cities = {"Chennai", "Madurai", "Hydrabad", "Coimbatore", "Bangaluru", "Delhi", "Salem", "Tuticorin", "Tirunelveli", "Erode", "Mumbai", "Kochin"};

		//@formatter:off
		for (int i = 0; i < count; i++) {
			result.add(new Employee(names[(int) (names.length * Math.random())], 
					i + 1000, 
					Double.parseDouble(df.format(Math.random() * 10000)), 
					sex[(int) (sex.length * Math.random())], 
					cities[(int)(cities.length * Math.random())]));
		}
		//@formatter:on

		return result;
	}

}
