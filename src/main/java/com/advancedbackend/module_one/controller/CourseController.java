package com.advancedbackend.module_one.controller;

import com.advancedbackend.module_one.domain.dto.Course;
import com.advancedbackend.module_one.rest.resource.CoursesApi;
import com.advancedbackend.module_one.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CourseController implements CoursesApi {
    private final CourseService courseService;

    @Override
    public ResponseEntity<Void> deleteCourse(Integer courseId) {
        log.info("DELETE API called, course id: {}", courseId);
        courseService.deleteById(courseId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Course> getCourse(Integer courseId) {
        log.info("GET API called, course id: {}", courseId);
        var courseResponse = courseService.getById(courseId);
        return ResponseEntity.ok(courseResponse);
    }

    @Override
    public ResponseEntity<Course> saveCourse(Course course) {
        log.info("POST API called, course request: {}", course);
        var courseResponse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
    }

    @Override
    public ResponseEntity<Course> updateCourse(Integer courseId, Course course) {
        log.info("PUT API called, course request: {}", course);
        var courseResponse = courseService.update(courseId, course);
        return ResponseEntity.ok(courseResponse);
    }
}
