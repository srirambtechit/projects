package com.learning.spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.learning.spring.bean.Student;

public class StudentDAOImpl implements StudentDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Student student) {
	String sql = String.format("insert into student (name, address, email) values ('%s', '%s', '%s')", student.getName(), student.getAddress(), student.getEmail());
	System.out.println(sql);
	return jdbcTemplate.update(sql);
    }

    @Override
    public int update(Student student) {
	String sql = String.format("update student set name='%s', address='%s', email='%s' where id='%s'", student.getName(), student.getAddress(), student.getEmail(), student.getId());
	System.out.println(sql);
	return jdbcTemplate.update(sql);
    }

    @Override
    public int delete(int id) {
	String sql = String.format("delete from student where id='%d'", id);
	System.out.println(sql);
	return jdbcTemplate.update(sql);
    }

    @Override
    public List<Student> fetchStudent() {
	String sql = String.format("select * from student");
	System.out.println(sql);
	List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
	List<Student> students = new ArrayList<>();
	if (list != null) {
	    for (Map<String, Object> map : list) {
		Student student = new Student();
		student.setId((Integer) map.get("id"));
		student.setName(String.valueOf(map.get("name")));
		student.setAddress(String.valueOf(map.get("address")));
		student.setEmail(String.valueOf(map.get("email")));
		students.add(student);
	    }
	}
	return students;
    }
}
