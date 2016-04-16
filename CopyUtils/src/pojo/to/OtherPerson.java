package pojo.to;

import java.util.Set;

public class OtherPerson {

	private String name;
	private int age;
	private Set<Integer> ints;
	private Set<ToAddress> addresses;

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

	public Set<ToAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<ToAddress> addresses) {
		this.addresses = addresses;
	}

}