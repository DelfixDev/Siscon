package com.siscon.siscontest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El primer nombre es requerido")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @NotBlank(message = "El apellido paterno es requerido")
    @Column(name = "last_name_paternal")
    private String lastNamePaternal;

    @Column(name = "last_name_maternal")
    private String lastNameMaternal;

    @NotNull(message = "La edad es requerida")
    @Min(value = 18, message = "La edad debe ser mayor o igual a 18")
    private Integer age;

    @NotBlank(message = "El sexo es requerido")
    @Size(min = 1, max = 1, message = "El sexo debe ser un carácter")
    private String gender;

    @NotNull(message = "La fecha de nacimiento es requerida")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(name = "birth_date", columnDefinition = "DATETIME")
    private LocalDate birthDate;

    @NotBlank(message = "El puesto es requerido")
    private String position;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;
}
