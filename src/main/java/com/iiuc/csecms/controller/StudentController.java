package com.iiuc.csecms.controller;

import com.iiuc.csecms.model.Student;
import com.iiuc.csecms.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return buildNotFoundResponse("Student", id);
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/student-id/{studentId}")
    public ResponseEntity<?> getStudentByStudentId(@PathVariable String studentId) {
        Student student = studentService.getStudentByStudentId(studentId);
        if (student == null) {
            return buildNotFoundResponse("Student with ID", studentId);
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Student>> getStudentsByDepartment(@PathVariable Long departmentId) {
        List<Student> students = studentService.getStudentsByDepartmentId(departmentId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<Student>> getStudentsBySemester(@PathVariable String semester) {
        List<Student> students = studentService.getStudentsBySemester(semester);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudent) {

        Student existing = studentService.getStudentById(id);
        if (existing == null) {
            return buildNotFoundResponse("Student", id);
        }

        Student savedStudent = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(savedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        boolean deleted = studentService.deleteStudent(id);

        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Student deleted successfully");
            response.put("id", id.toString());
            return ResponseEntity.ok(response);
        } else {
            return buildNotFoundResponse("Student", id);
        }
    }

    private ResponseEntity<Map<String, String>> buildNotFoundResponse(String resource, Object identifier) {
        Map<String, String> error = new HashMap<>();
        error.put("error", resource + " not found with identifier: " + identifier);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}