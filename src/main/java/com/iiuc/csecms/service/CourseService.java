package com.iiuc.csecms.service;

import com.iiuc.csecms.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CourseService {

    private final DepartmentService departmentService;
    private final AtomicLong nextId = new AtomicLong(1);
    private final List<Course> courses = new ArrayList<>();

    // Constructor injection
    public CourseService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    //  CREATE
    public Course addCourse(Course course) {

        if (course.getId() == null) {
            course.setId(nextId.getAndIncrement());
        }

        course.setCode(course.getCode().toUpperCase());
        courses.add(course);
        return course;
    }

    //  READ ALL
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    //  READ BY ID
    public Course getCourseById(Long id) {
        Optional<Course> course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return course.orElse(null);
    }

    //  READ BY DEPARTMENT
    public List<Course> getCoursesByDepartmentId(Long departmentId) {
        return courses.stream()
                .filter(course -> course.getDepartmentId().equals(departmentId))
                .toList();
    }

    //  UPDATE
    public Course updateCourse(Long id, Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(id)) {
                updatedCourse.setId(id);
                courses.set(i, updatedCourse);
                return updatedCourse;
            }
        }
        return null;
    }

    //  DELETE
    public boolean deleteCourse(Long id) {
        return courses.removeIf(course -> course.getId().equals(id));
    }
}
