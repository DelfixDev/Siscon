package com.siscon.siscontest.service;

import com.siscon.siscontest.exception.EmployeeNotFoundException;
import com.siscon.siscontest.model.Employee;
import com.siscon.siscontest.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Obteniendo todos los empleados.");
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        logger.info("Obteniendo empleado con ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        logger.warn("Eliminando empleado con ID: {}", id);
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("No se puede eliminar. Empleado no encontrado con ID: " + id);
        }
        employeeRepository.deleteById(id);
        logger.info("Empleado con ID {} eliminado exitosamente.", id);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee employee) {
        logger.info("Actualizando empleado con ID: {}", id);
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setSecondName(employee.getSecondName());
                    existingEmployee.setLastNamePaternal(employee.getLastNamePaternal());
                    existingEmployee.setLastNameMaternal(employee.getLastNameMaternal());
                    existingEmployee.setAge(employee.getAge());
                    existingEmployee.setGender(employee.getGender());
                    existingEmployee.setBirthDate(employee.getBirthDate());
                    existingEmployee.setPosition(employee.getPosition());
                    return employeeRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new EmployeeNotFoundException("No se puede actualizar. Empleado no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public List<Employee> insertEmployees(List<Employee> employees) {
        logger.info("Insertando {} empleados.", employees.size());
        return employeeRepository.saveAll(employees);
    }
}
