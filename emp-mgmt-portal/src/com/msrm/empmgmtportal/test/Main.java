package com.msrm.empmgmtportal.test;

import com.msrm.empmgmtportal.module.dal.dao.EmployeeDAO;
import com.msrm.empmgmtportal.module.dal.dao.impl.EmployeeDAOImpl;
import com.msrm.empmgmtportal.module.dal.dos.EmployeeDO;

public class Main {

	public static void main(String[] args) {
		EmployeeDAO dao = new EmployeeDAOImpl();

		EmployeeDO employeeDO = new EmployeeDO();
		employeeDO.setName("Praveen");

		Integer id = dao.insert(employeeDO);
		System.out.println("Inserted: ID: " + id);
	}

}
