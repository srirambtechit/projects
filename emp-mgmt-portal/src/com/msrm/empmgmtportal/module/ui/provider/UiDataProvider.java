package com.msrm.empmgmtportal.module.ui.provider;

import org.springframework.beans.BeanUtils;

import com.msrm.empmgmtportal.module.biz.bos.EmployeeBO;
import com.msrm.empmgmtportal.module.ui.tos.EmployeeTO;

public class UiDataProvider {

	public static EmployeeBO toToBO(EmployeeTO employeeTO) {
		if (employeeTO == null)
			return null;
		EmployeeBO employeeBO = new EmployeeBO();
		BeanUtils.copyProperties(employeeTO, employeeBO);
		return employeeBO;
	}

}
