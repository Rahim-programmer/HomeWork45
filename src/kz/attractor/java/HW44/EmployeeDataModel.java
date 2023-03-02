package kz.attractor.java.HW44;

import FileService.FileEmployeeService;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDataModel {
    private Employee employee;
    private List<Employee> employees;

    public EmployeeDataModel(Employee employee) {
        this.employee = employee;
        this.employees = FileEmployeeService.readFile();
    }

    public EmployeeDataModel() {
        this.employees = FileEmployeeService.readFile();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
