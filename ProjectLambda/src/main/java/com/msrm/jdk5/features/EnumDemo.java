package com.msrm.jdk5.features;

public class EnumDemo {

	public enum Status {
		COMPLETE, INPROGRESS, FAILED, SUCCESS
	}

	public static void main(String[] args) {
		System.out.println(Status.COMPLETE);
		Status success = Status.SUCCESS;
		System.out.println(success);
	}
}
