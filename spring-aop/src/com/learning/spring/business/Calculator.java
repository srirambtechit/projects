package com.learning.spring.business;

public class Calculator {

    public int add(int a, int b) {
	doStuff();
	int c;
	c = a + b;
	return c;
    }

    public int sub(int a, int b) {
	int c;
	c = a - b;
	return c;
    }

    public int mul(int a, int b) {
	int c;
	c = a * b;
	return c;
    }

    public int div(int a, int b) throws ArithmeticException {
	int c;
	c = a / b;
	return c;
    }
    
    private void doStuff() {
	System.out.println("doStuff");
    }

}
