package com.iiuc.csecms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Long id;

    @NotBlank(message = "Department code is required")
    @Size(min = 2, max = 10, message = "Department code must be 2-10 characters")
    @Pattern(regexp = "^[A-Z]+$", message = "Department code must be uppercase letters only")
    private String code;  // Examples: CSE, EEE, BBA, LAW, ENG, ISL

    @NotBlank(message = "Department name is required")
    @Size(min = 5, max = 100, message = "Department name must be 5-100 characters")
    private String name;  // Examples: Computer Science and Engineering

    @NotBlank(message = "Dean name is required")
    @Size(min = 3, max = 50, message = "Dean name must be 3-50 characters")
    private String dean;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;  // department email

    @Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "Valid phone number is required")
    private String phone;
}
