package com.iiuc.csecms.service;

import com.iiuc.csecms.model.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DepartmentService {

    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Department> departments = new ArrayList<>();

    public Department addDepartment(Department department) {
        if (department.getId() == null) {
            department.setId(nextId.getAndIncrement());
        }
        departments.add(department);
        return department;
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

    public Department getDepartmentById(Long id) {
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getId().equals(id)) {
                updatedDepartment.setId(id);
                departments.set(i, updatedDepartment);
                return updatedDepartment;
            }
        }
        return null;
    }

    public boolean deleteDepartment(Long id) {
        return departments.removeIf(dept -> dept.getId().equals(id));
    }
}