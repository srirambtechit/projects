package com.learning.spring.app;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.learning.spring.bean.Student;
import com.learning.spring.dao.StudentDAO;

public class Application {

    public static void main(String[] args) {

	ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

	StudentDAO studentDAO = ctx.getBean("studentDAO", StudentDAO.class);

	// Inserting a new record
	Student student = new Student(0, "Sriram", "Chennai", "sriram@gmail.com");
	System.out.println("Insert new record");
	studentDAO.save(student);

	System.out.println("Update record");
	// pass student record id to perform update
	student.setId(1);
	student.setAddress("Madurai");
	studentDAO.update(student);

	List<Student> students = studentDAO.fetchStudent();
	if (students != null) {
	    for (Student s : students) {
		System.out.println(s);
	    }
	}

    }

}
