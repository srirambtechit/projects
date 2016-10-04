package com.msrm.empmgmtportal.module.dal.dao;

import java.util.List;

import com.msrm.empmgmtportal.module.dal.dos.EmployeeDO;

public interface EmployeeDAO extends BaseDAO<EmployeeDO> {

	EmployeeDO selectById(int id);

	List<EmployeeDO> selectAll();

}
