package com.msrm.jdk5.features;

public class GenericsExample {

	public static void main(String[] args) {
		Short[] numbers = {3, 2, 9, 1, 8, 6};
		System.out.println(max(numbers));
	}

	public static <T extends Comparable<T>> T max(T[] nums) {
		T max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (max.compareTo(nums[i]) < 0)
				max = nums[i];
		}
		return max;
	}

	public static Integer max(Integer[] nums) {
		Integer max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (max < nums[i])
				max = nums[i];
		}
		return max;
	}

	public static Byte max(Byte[] nums) {
		Byte max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (max < nums[i])
				max = nums[i];
		}
		return max;
	}

	public static Short max(Short[] nums) {
		Short max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (max < nums[i])
				max = nums[i];
		}
		return max;
	}

}
