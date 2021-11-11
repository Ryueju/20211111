package com.yedam.member;

public class DataTable {
	String name;
	   String extn;
	   String office;
	   String position;
	   String salary;
	   String startDate;
	@Override
	public String toString() {
		return "DataTable [name=" + name + ", extn=" + extn + ", office=" + office + ", position=" + position
				+ ", salary=" + salary + ", startDate=" + startDate + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtn() {
		return extn;
	}
	public void setExtn(String extn) {
		this.extn = extn;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
