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
public class Faculty {

    private Long id;

    @NotBlank(message = "Faculty name is required")
    @Size(min = 3, max = 50, message = "Name must be 3-50 characters")
    private String name;

    @NotBlank(message = "Designation is required")
    @Pattern(regexp = "^(Professor|Associate Professor|Assistant Professor|Lecturer)$",
            message = "Designation must be: Professor, Associate Professor, Assistant Professor, or Lecturer")
    private String designation;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be positive")
    private Long departmentId;

    @Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "Valid phone number is required")
    private String phone;
}