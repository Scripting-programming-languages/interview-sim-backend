package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.entity.CourseLevel;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.mapper.CourseMapper;
import com.github.scripting.programming.language.interview_sim_backend.repository.CourseRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.CourseService;
import com.github.scripting.programming.language.model.CourseDetail;
import com.github.scripting.programming.language.model.CourseDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseDto> getCourses(@Nullable Long categoryId, @Nullable String level) {
        CourseLevel levelEnum = null;
        if (level != null) {
            levelEnum = CourseLevel.findByName(level)
                    .orElseThrow(() -> new BaseApiException(HttpStatus.BAD_REQUEST, "Такого уровня курса не существует"));
        }

        var rawCourses = courseRepository.findAllWithCategoryByCategoryIdAndCourseLevel(categoryId, levelEnum);
        return rawCourses.stream()
                .map(courseMapper::toCourseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDetail getCourse(Long courseId) {
        var course = courseRepository.findWithCategoriesById(courseId)
                .orElseThrow(() -> new BaseApiException(HttpStatus.BAD_REQUEST, "Такого курса не существует"));

        // Сourse с таким ID уже есть в памяти, просто инициализируется поле questions.
        courseRepository.findWithQuestionsById(courseId);
        return courseMapper.toCourseDetail(course);
    }
}
