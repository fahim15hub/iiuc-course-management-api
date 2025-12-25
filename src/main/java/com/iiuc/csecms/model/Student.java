package com.iiuc.csecms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;
    private String studentId;      // Like "2024001"
    private String name;
    private String email;
    private Long departmentId;     // References Department
    private String phone;
    private String semester;       // Like "Spring2024"
    private String status = "Active";
}