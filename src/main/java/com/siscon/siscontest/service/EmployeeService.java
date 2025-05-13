package com.siscon.siscontest.service;

import com.siscon.siscontest.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void deleteEmployee(Long id);
    Employee updateEmployee(Long id, Employee employee);
    List<Employee> insertEmployees(List<Employee> employees);
}
