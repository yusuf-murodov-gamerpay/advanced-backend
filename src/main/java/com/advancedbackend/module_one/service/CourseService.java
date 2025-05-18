package com.advancedbackend.module_one.service;

import com.advancedbackend.module_one.domain.dto.Course;
import com.advancedbackend.module_one.repository.CourseRepository;
import com.advancedbackend.module_one.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService {
    private final CourseMapper mapper;
    private final CourseRepository repository;
    private final CustomMetrics customMetrics;

    @Transactional
    public Course save(Course courseDTO) {
        log.info("Saving new course: {}", courseDTO);
        var course = mapper.toCourseEntity(courseDTO);
        var savedCourse = repository.save(course);
        return mapper.toCourseDto(savedCourse);
    }

    @Transactional
    public Course update(int id, Course courseDTO) {
        boolean isExisted = repository.existsById(id);
        if(!isExisted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id=" + id);
        }
        return save(courseDTO);
    }

    public Course getById(int id) {
        log.info("Getting course by id: {}", id);
        customMetrics.processSomething();
        return repository.findById(id)
                .map(mapper::toCourseDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id=" + id));
    }

    public void deleteById(int id) {
        log.info("Deleting course by id: {}", id);
        repository.deleteById(id);
    }
}
