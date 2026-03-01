package com.github.scripting.programming.language.interview_sim_backend.controller;

import com.github.scripting.programming.language.controller.CoursesApi;
import com.github.scripting.programming.language.interview_sim_backend.service.CourseService;
import com.github.scripting.programming.language.model.CourseDetail;
import com.github.scripting.programming.language.model.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseControllerImpl implements CoursesApi {
    private final CourseService courseService;
    @Override
    public ResponseEntity<CourseDetail> coursesCourseIdGet(Long courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    @Override
    public ResponseEntity<List<CourseDto>> coursesGet(Long categoryId, String level) {
        return ResponseEntity.ok(courseService.getCourses(categoryId, level));
    }
}
