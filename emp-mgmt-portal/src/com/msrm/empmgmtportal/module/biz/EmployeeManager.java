package com.msrm.empmgmtportal.module.biz;

import com.msrm.empmgmtportal.module.biz.bos.EmployeeBO;
import com.msrm.empmgmtportal.module.biz.provider.BizDataProvider;
import com.msrm.empmgmtportal.module.dal.dao.EmployeeDAO;

public class EmployeeManager {

	private EmployeeDAO employeeDAO;

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public void store(EmployeeBO employeeBO) {
		employeeDAO.insert(BizDataProvider.boToDo(employeeBO));
	}

}
