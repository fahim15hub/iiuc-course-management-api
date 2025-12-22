package com.iiuc.csecms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Course {

    private Long id;            // Unique ID
    private String code;        // Course code
    private String title;       // Course title
    private double credit;         // Credit hours
    private String type;        // Theory / Lab
    private String semester;    // e.g., Fall 2025
    private Long departmentId;  // Department ID
    private String teacher;     // Teacher name

}