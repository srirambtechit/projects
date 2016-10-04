package com.msrm.empmgmtportal.module.biz.provider;

import org.springframework.beans.BeanUtils;

import com.msrm.empmgmtportal.module.biz.bos.EmployeeBO;
import com.msrm.empmgmtportal.module.dal.dos.EmployeeDO;

public final class BizDataProvider {

	private BizDataProvider() {
	}

	public static EmployeeBO doToBo(EmployeeDO employeeDO) {
		if (employeeDO == null)
			return null;
		EmployeeBO employeeBO = new EmployeeBO();
		BeanUtils.copyProperties(employeeDO, employeeBO);
		return employeeBO;
	}

	public static EmployeeDO boToDo(EmployeeBO employeeBO) {
		if (employeeBO == null)
			return null;
		EmployeeDO employeeDO = new EmployeeDO();
		BeanUtils.copyProperties(employeeBO, employeeDO);
		return employeeDO;
	}

}
