package com.iiuc.csecms.controller;

import com.iiuc.csecms.model.Faculty;
import com.iiuc.csecms.service.FacultyService;
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
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<?> addFaculty(@Valid @RequestBody Faculty faculty,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }

        Faculty savedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFaculty);
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFaculty() {
        List<Faculty> faculties = facultyService.getAllFaculty();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);

        if (faculty == null) {
            return buildNotFoundResponse("Faculty", id);
        }

        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Faculty>> getFacultyByDepartment(@PathVariable Long departmentId) {
        List<Faculty> faculties = facultyService.getFacultyByDepartmentId(departmentId);
        return ResponseEntity.ok(faculties);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFaculty(
            @PathVariable Long id,
            @Valid @RequestBody Faculty updatedFaculty,
            BindingResult bindingResult) {

        Faculty existing = facultyService.getFacultyById(id);
        if (existing == null) {
            return buildNotFoundResponse("Faculty", id);
        }

        if (bindingResult.hasErrors()) {
            return buildValidationErrorResponse(bindingResult);
        }

        Faculty savedFaculty = facultyService.updateFaculty(id, updatedFaculty);
        return ResponseEntity.ok(savedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) {
        boolean deleted = facultyService.deleteFaculty(id);

        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Faculty deleted successfully");
            response.put("id", id.toString());
            return ResponseEntity.ok(response);
        } else {
            return buildNotFoundResponse("Faculty", id);
        }
    }

    // ===== HELPER METHODS (Same as other controllers) =====

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
