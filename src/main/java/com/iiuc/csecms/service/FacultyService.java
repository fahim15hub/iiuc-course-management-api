package com.iiuc.csecms.service;

import com.iiuc.csecms.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FacultyService {

    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Faculty> faculties = new ArrayList<>();


    public Faculty addFaculty(Faculty faculty) {
        if (faculty.getId() == null) {
            faculty.setId(nextId.getAndIncrement());
        }
        faculties.add(faculty);
        return faculty;
    }

    public List<Faculty> getAllFaculty() {
        return new ArrayList<>(faculties);
    }

    public Faculty getFacultyById(Long id) {
        return faculties.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        for (int i = 0; i < faculties.size(); i++) {
            Faculty existing = faculties.get(i);
            if (existing.getId().equals(id)) {
                updatedFaculty.setId(id);
                faculties.set(i, updatedFaculty);
                return updatedFaculty;
            }
        }
        return null;
    }

    public boolean deleteFaculty(Long id) {
        return faculties.removeIf(faculty -> faculty.getId().equals(id));
    }

    // Get faculty by department
    public List<Faculty> getFacultyByDepartmentId(Long departmentId) {
        return faculties.stream()
                .filter(f -> f.getDepartmentId().equals(departmentId))
                .toList();
    }
}