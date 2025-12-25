package com.iiuc.csecms.controller;

import com.iiuc.csecms.model.Course;
import com.iiuc.csecms.service.CourseService;
import com.iiuc.csecms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    // Create / Add a course
    @PostMapping
    public ResponseEntity<?> addCourse(@Valid @RequestBody Course course,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }

        Course savedCourse = courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);

        if (course == null) {
            return buildNotFoundResponse("Course", id);
        }

        return ResponseEntity.ok(course);
    }

    //  Get courses by department ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Course>> getCoursesByDepartment(@PathVariable Long departmentId) {
        List<Course> courses = courseService.getCoursesByDepartmentId(departmentId);
        return ResponseEntity.ok(courses);
    }

    // Update course by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody Course updatedCourse,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }

        Course existing = courseService.getCourseById(id);
        if (existing == null) {
            return buildNotFoundResponse("Course", id);
        }

        Course savedCourse = courseService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(savedCourse);
    }

    // Delete course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteCourse(id);

        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Course deleted successfully");
            response.put("id", id.toString());
            return ResponseEntity.ok(response);
        } else {
            return buildNotFoundResponse("Course", id);
        }
    }



    private ResponseEntity<Map<String, String>> buildValidationErrorResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private ResponseEntity<Map<String, String>> buildNotFoundResponse(String resource, Object identifier) {
        Map<String, String> error = new HashMap<>();
        error.put("error", resource + " not found with identifier: " + identifier);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/student/{studentId}/current-courses")
    public ResponseEntity<?> getStudentCurrentCourses(@PathVariable Long studentId) {
        // 1. Get student
        com.iiuc.csecms.model.Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return buildNotFoundResponse("Student", studentId);
        }

        // 2. Get courses for student's department and semester
        java.util.List<com.iiuc.csecms.model.Course> courses = courseService.getAllCourses().stream()
                .filter(c -> student.getDepartmentId() != null &&
                        c.getDepartmentId() != null &&
                        c.getDepartmentId().equals(student.getDepartmentId()))
                .filter(c -> student.getSemester() != null &&
                        c.getSemester() != null &&
                        c.getSemester().equalsIgnoreCase(student.getSemester()))
                .toList();

        return ResponseEntity.ok(courses);
    }
}