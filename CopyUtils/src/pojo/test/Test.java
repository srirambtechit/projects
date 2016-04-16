package pojo.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import pojo.from.Address;
import pojo.from.Person;
import pojo.to.OtherPerson;

/**
 * Class for test copy properties
 *
 * @author Rene Enriquez
 * @date 23/07/2012
 *
 */
public class Test {

	/**
	 * Main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Person person = new Person();
		person.setAge(15);
		person.setName("rene");
		person.setInts(new HashSet<Integer>(Arrays.asList(11, 22, 323)));
		Address address = new Address();
		address.setNo(63);
		address.setState("Tamilnadu");
		address.setCity("Chennai");
		address.setStreet("1st cross street");
		address.setCountry("India");
		Set<Address> addressSet = new HashSet<>();
		person.setAddresses(addressSet);

		OtherPerson othePerson = new OtherPerson();

		System.out.println("*** Before BeanUtils.copyProperties ***");

		System.out.println("Person:--");
		System.out.println(person.getAge());
		System.out.println(person.getName());
		System.out.println(person.getInts());
		System.out.println(person.getAddresses());

		System.out.println("othePerson:--");
		System.out.println(othePerson.getAge());
		System.out.println(othePerson.getName());
		System.out.println(othePerson.getInts());
		System.out.println(othePerson.getAddresses());

		BeanUtils.copyProperties(person, othePerson);

		System.out.println("\n*** After BeanUtils.copyProperties ***");

		System.out.println("Person:--");
		System.out.println(person.getAge());
		System.out.println(person.getName());
		System.out.println(person.getInts());
		System.out.println(person.getAddresses());

		System.out.println("othePerson:--");
		System.out.println(othePerson.getAge());
		System.out.println(othePerson.getName());
		System.out.println(othePerson.getInts());
		System.out.println(othePerson.getAddresses());
	}
}