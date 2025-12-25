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
public class Course {

    private Long id;

    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 10, message = "Course code must be 3-10 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Course code must be uppercase alphanumeric")
    private String code;

    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 100, message = "Title must be 3-100 characters")
    private String title;

    @Positive(message = "Credit must be greater than 0")
    @DecimalMin(value = "0.5", message = "Credit must be at least 0.5")
    @DecimalMax(value = "6.0", message = "Credit cannot exceed 6.0")
    private Double credit;

    @NotBlank(message = "Course type is required")
    @Pattern(regexp = "^(Theory|Lab|Project)$",
            message = "Type must be: Theory, Lab, Project")
    private String type;

    @NotBlank(message = "Semester is required")
    @Pattern(regexp = "^(Autumn|Spring)[0-9]{4}$",
            message = "Semester format: Fall2024, Spring2025, etc.")
    private String semester;

    @NotNull(message = "Department ID is required")
    @Positive(message = "Department ID must be positive")
    private Long departmentId;

    @NotBlank(message = "Teacher name is required")
    @Size(min = 2, max = 50, message = "Teacher name must be 2-50 characters")
    @Pattern(regexp = "^[A-Za-z .'-]+$", message = "Teacher name contains invalid characters")
    private String teacher;
    private Long facultyID;
}
