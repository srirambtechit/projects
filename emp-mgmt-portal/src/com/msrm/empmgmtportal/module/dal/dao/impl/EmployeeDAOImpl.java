package com.msrm.empmgmtportal.module.dal.dao.impl;

import java.util.List;

import com.msrm.empmgmtportal.module.dal.dao.EmployeeDAO;
import com.msrm.empmgmtportal.module.dal.dos.EmployeeDO;

public class EmployeeDAOImpl extends BaseDAOImpl<EmployeeDO> implements EmployeeDAO {

	@Override
	public EmployeeDO selectById(int id) {
		return super.selectById(EmployeeDO.class, id);
	}

	@Override
	public List<EmployeeDO> selectAll() {
		return super.selectAll(EmployeeDO.class);
	}

}
