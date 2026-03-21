package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.model.CourseCreateRequest;
import com.github.scripting.programming.language.model.CourseDetail;
import com.github.scripting.programming.language.model.CourseDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface CourseService {
    List<CourseDto> getCourses(@Nullable Long categoryId, @Nullable String level);

    CourseDetail getCourse(@NotNull Long courseId);

    void deleteCourse(Long courseId);

    CourseDto updateCourse(Long courseId, CourseCreateRequest courseCreateRequest);

    CourseDto createCourse(CourseCreateRequest courseCreateRequest);
}
