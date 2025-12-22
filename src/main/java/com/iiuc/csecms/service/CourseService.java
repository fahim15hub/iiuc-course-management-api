package com.iiuc.csecms.service;
import com.iiuc.csecms.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    // In-memory list to store courses
    private final List<Course> courses = new ArrayList<>();

    // Create / Add a new course
    public Course addCourse(Course course) {
        courses.add(course);
        return course;
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courses;
    }

    // Get a course by ID
    public Course getCourseById(Long id) {
        Optional<Course> course = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return course.orElse(null);
    }

    // Update a course
    public Course updateCourse(Long id, Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId().equals(id)) {
                courses.set(i, updatedCourse);
                return updatedCourse;
            }
        }
        return null;
    }

    // Delete a course
    public boolean deleteCourse(Long id) {
        return courses.removeIf(course -> course.getId().equals(id));
    }
}

