package com.msrm.empmgmtportal.module.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.msrm.empmgmtportal.module.biz.EmployeeManager;
import com.msrm.empmgmtportal.module.ui.provider.UiDataProvider;
import com.msrm.empmgmtportal.module.ui.tos.EmployeeTO;

@Controller
public class ApplicationUIController {

	@Autowired
	private EmployeeManager employeeManager;

	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	@RequestMapping("/welcome")
	public ModelAndView welcomePage() {
		String message = "Welcome to Employee Management Portal Application";
		return new ModelAndView("welcomeScreen", "content", message);
	}

	@RequestMapping("/hello")
	public ModelAndView helloPage() {
		return new ModelAndView("helloScreen", "msg", "Welcome, Message for testing purpose");
	}

	@RequestMapping("/employeeForm")
	public ModelAndView employeeFormProcessor() {
		EmployeeTO employeeTO = new EmployeeTO();
		employeeManager.store(UiDataProvider.toToBO(employeeTO));
		return new ModelAndView("welcomeScreen", "content", "Employee details saved successfully");
	}

}