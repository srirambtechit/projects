package pojo.from;

import java.util.Set;

public class Person {

	private String name;
	private int age;
	private Set<Integer> ints;
	private Set<Address> addresses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Integer> getInts() {
		return ints;
	}

	public void setInts(Set<Integer> ints) {
		this.ints = ints;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

}