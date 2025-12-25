package com.iiuc.csecms.service;

import com.iiuc.csecms.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {

    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Student> students = new ArrayList<>();

    public Student addStudent(Student student) {
        if (student.getId() == null) {
            student.setId(nextId.getAndIncrement());
        }
        students.add(student);
        return student;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Student getStudentByStudentId(String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId() != null && s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getStudentsByDepartmentId(Long departmentId) {
        return students.stream()
                .filter(s -> s.getDepartmentId() != null && s.getDepartmentId().equals(departmentId))
                .toList();
    }

    public List<Student> getStudentsBySemester(String semester) {
        return students.stream()
                .filter(s -> s.getSemester() != null && s.getSemester().equalsIgnoreCase(semester))
                .toList();
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            Student existing = students.get(i);
            if (existing.getId().equals(id)) {
                updatedStudent.setId(id);
                students.set(i, updatedStudent);
                return updatedStudent;
            }
        }
        return null;
    }

    public boolean deleteStudent(Long id) {
        return students.removeIf(student -> student.getId().equals(id));
    }
}