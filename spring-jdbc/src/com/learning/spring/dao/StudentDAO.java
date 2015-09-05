package com.learning.spring.dao;

import java.util.List;

import com.learning.spring.bean.Student;

public interface StudentDAO {

    int save(Student student);

    int update(Student student);

    int delete(int id);

    List<Student> fetchStudent();

}
