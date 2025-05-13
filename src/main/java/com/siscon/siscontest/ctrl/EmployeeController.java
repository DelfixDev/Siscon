package com.siscon.siscontest.ctrl;

import com.siscon.siscontest.model.Employee;
import com.siscon.siscontest.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @Operation(summary = "Obtiene todos los empleados registrados")
    @ApiResponse(responseCode = "200", description = "Lista de empleados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
    }
    @Operation(summary = "Obtiene un empleado por su ID")
    @ApiResponse(responseCode = "200", description = "Empleado encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }
    @Operation(summary = "Elimina un empleado por su ID")
    @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Actualiza la información de un empleado")
    @ApiResponse(responseCode = "200", description = "Empleado actualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class)))
    @ApiResponse(responseCode = "400", description = "Datos de empleado inválidos")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
    }
    @Operation(summary = "Inserta uno o varios empleados")
    @ApiResponse(responseCode = "201", description = "Empleados creados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @PostMapping
    public ResponseEntity<List<Employee>> insertEmployees(@Valid @RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.insertEmployees(employees), HttpStatus.CREATED);
    }
}
