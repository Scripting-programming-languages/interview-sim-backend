package com.github.scripting.programming.language.interview_sim_backend.controller;

import com.github.scripting.programming.language.controller.CoursesAdminApi;
import com.github.scripting.programming.language.interview_sim_backend.service.CourseService;
import com.github.scripting.programming.language.model.CourseCreateRequest;
import com.github.scripting.programming.language.model.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseAdminControllerImpl implements CoursesAdminApi {
    private final CourseService courseService;

    @Override
    public ResponseEntity<Void> adminCoursesCourseIdDelete(Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CourseDto> adminCoursesCourseIdPut(Long courseId, CourseCreateRequest courseCreateRequest) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, courseCreateRequest));
    }

    @Override
    public ResponseEntity<CourseDto> adminCoursesPost(CourseCreateRequest courseCreateRequest) {
        return ResponseEntity.ok(courseService.createCourse(courseCreateRequest));
    }
}
