package com.msrm.jdk5.features;

class A {
	public A method() {
		System.out.println("A");
		return this;
	}
}

class B extends A {
	// overriding though return type changed
	// because B is sub class of A which will be
	// called as Covariant type
	public B method() {
		System.out.println("B");
		return this;
	}
}

public class CovariantType {

	public static void main(String[] args) {
		A a = new B();
		a.method();
	}

}
