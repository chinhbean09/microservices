package com.tanthanh.employeeservice.command.api.events;

public class EmployeeDeletedEvent {
	private String employeeId;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	
}
