package com.siscon.siscontest.service;

import com.siscon.siscontest.exception.EmployeeNotFoundException;
import com.siscon.siscontest.model.Employee;
import com.siscon.siscontest.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp(){
        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Fidelmar");
        employee1.setLastNamePaternal("Cruz");
        employee1.setLastNameMaternal("Guzman");
        employee1.setAge(36);
        employee1.setGender("M");
        employee1.setBirthDate(LocalDate.of(1989, 4,25));
        employee1.setPosition("Developper");

        employee2 = new Employee();
        employee2.setId(1L);
        employee2.setFirstName("Mario");
        employee2.setLastNamePaternal("Mendez");
        employee2.setLastNameMaternal("Agustino");
        employee2.setAge(31);
        employee2.setGender("M");
        employee2.setBirthDate(LocalDate.of(1995, 5,12));
        employee2.setPosition("RRHH");
    }
    @Test
    void getAllEmployees_shouldReturnListOfEmployees(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }
    @Test
    void getEmployeeById_shouldReturnEmployee_whenIdExists(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        Employee employee =employeeService.getEmployeeById(1L);
        assertNotNull(employee);
        assertEquals("Fidelmar", employee.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }
    @Test
    void getEmployeeById_shouldThrowEmployeeNotFoundException_whenIdDoesNotExist(){
        when(employeeRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, ()->employeeService.getEmployeeById(3L));
        verify(employeeRepository, times(1)).findById(3L);
    }
    @Test
    void deleteEmployee_shouldDeleteEmployee_whenIdExists(){
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);
        employeeService.deleteEmployee(1L);
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository,times(1)).deleteById(1L);
    }
    @Test
    void deleteEmployee_shouldThrowEmployeeNotFoundException_whenIdDoesNotExist(){
        when(employeeRepository.existsById(3L)).thenReturn(false);
        assertThrows(EmployeeNotFoundException.class, ()-> employeeService.deleteEmployee(3L));
        verify(employeeRepository, times(1)).existsById(3L);
        verify(employeeRepository,times(1)).deleteById(anyLong());
    }
    @Test
    void updateEmployee_shouldUpdateEmployee_whenIdExists(){
        Employee employee2Update = new Employee();
        employee2Update.setFirstName("Nombre actualizado");
        employee2Update.setLastNamePaternal("Apellido actualizado");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee2Update);
        Employee result = employeeService.updateEmployee(1L, employee2Update);
        assertNotNull(result);
        assertEquals("Nombre actualizado", result.getFirstName());
        assertEquals("Apellido actualizado", result.getLastNamePaternal());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    @Test
    void updateEmployee_shouldThrowEmployeeNotFoundException_whenIdDoesNotExist(){
        Employee employee2Update = new Employee();
        when(employeeRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(3L, employee2Update));
        verify(employeeRepository, times(1)).findById(3L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void insertEmployees_shouldSaveAllEmployees(){
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.saveAll(employees)).thenReturn(employees);
        List<Employee> insertedEmployees = employeeService.insertEmployees(employees);
        assertEquals(2, insertedEmployees.size());
        verify(employeeRepository, times(1)).saveAll(employees);
    }
}
