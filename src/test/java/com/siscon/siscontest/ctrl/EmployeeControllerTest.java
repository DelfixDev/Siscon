package com.siscon.siscontest.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siscon.siscontest.model.Employee;
import com.siscon.siscontest.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private EmployeeService employeeService;
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
    void getAllEmployees_shouldReturnOkAndListOfEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAllEmployees()).thenReturn(employees);
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Fidelmar"))
                .andExpect(jsonPath("$[1].firstName").value("Mario"));
        verify(employeeService, times(1)).getAllEmployees();
    }
    @Test
    void getEmployeeById_shouldReturnOkAndEmployee_whenIdExists() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(employee1);
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Fidelmar"));
        verify(employeeService, times(1)).getEmployeeById(1L);
    }
    @Test
    void deleteEmployee_shouldReturnNoContent_whenIdExists() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);
        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }
    @Test
    void updateEmployee_shouldReturnOkAndUpdatedEmployee_whenIdExistsAndDataIsValid() throws Exception {
        Employee updatedEmployee = new Employee();
        updatedEmployee.setFirstName("Nombre actualizado");
        updatedEmployee.setLastNamePaternal("Apellido actualizado");
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updatedEmployee);
        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Nombre actualizado"))
                .andExpect(jsonPath("$.lastNamePaternal").value("Apellido actualizado"));
        verify(employeeService, times(1)).updateEmployee(eq(1L), any(Employee.class));
    }
    @Test
    void updateEmployee_shouldReturnBadRequest_whenDataIsInvalid() throws Exception {
        Employee invalidEmployee = new Employee();
        invalidEmployee.setAge(10); // Edad inválida
        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age").doesNotExist()) // Asegurarse de que el objeto raíz no tiene 'age'
                .andExpect(jsonPath("$.errors.age").value("La edad debe ser mayor o igual a 18"));
        verify(employeeService, never()).updateEmployee(anyLong(), any(Employee.class));
    }
    @Test
    void insertEmployees_shouldReturnCreatedAndListOfInsertedEmployees_whenDataIsValid() throws Exception {
        List<Employee> employeesToInsert = Arrays.asList(employee1, employee2);
        when(employeeService.insertEmployees(employeesToInsert)).thenReturn(employeesToInsert);
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeesToInsert)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Fidelmar"))
                .andExpect(jsonPath("$[1].firstName").value("Mario"));
        verify(employeeService, times(1)).insertEmployees(employeesToInsert);
    }
    @Test
    void insertEmployees_shouldReturnBadRequest_whenDataIsInvalid() throws Exception {
        Employee invalidEmployee = new Employee();
        invalidEmployee.setFirstName("");
        List<Employee> employees = Arrays.asList(invalidEmployee);
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].firstName").doesNotExist())
                .andExpect(jsonPath("$.[0].errors.firstName").value("El primer nombre es requerido"));
        verify(employeeService, never()).insertEmployees(anyList());
    }
}
