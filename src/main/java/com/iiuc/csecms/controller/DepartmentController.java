package com.iiuc.csecms.controller;

import com.iiuc.csecms.model.Department;
import com.iiuc.csecms.service.DepartmentService;
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
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> addDepartment(@Valid @RequestBody Department department,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }
        Department saved = departmentService.addDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept == null) {
            return buildNotFoundResponse("Department", id);
        }
        return ResponseEntity.ok(dept);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody Department updatedDepartment,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }

        Department existing = departmentService.getDepartmentById(id);
        if (existing == null) {
            return buildNotFoundResponse("Department", id);
        }

        Department saved = departmentService.updateDepartment(id, updatedDepartment);
        return ResponseEntity.ok(saved);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        boolean deleted = departmentService.deleteDepartment(id);

        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Department deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            return buildNotFoundResponse("Department", id);
        }
    }

    // HELPER METHODS
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
}