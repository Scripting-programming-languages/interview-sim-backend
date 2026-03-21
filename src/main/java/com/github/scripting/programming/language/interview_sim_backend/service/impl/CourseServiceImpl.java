package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.entity.Category;
import com.github.scripting.programming.language.interview_sim_backend.entity.Course;
import com.github.scripting.programming.language.interview_sim_backend.entity.CourseLevel;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import com.github.scripting.programming.language.interview_sim_backend.exception.BaseApiException;
import com.github.scripting.programming.language.interview_sim_backend.mapper.CourseMapper;
import com.github.scripting.programming.language.interview_sim_backend.repository.CategoryRepository;
import com.github.scripting.programming.language.interview_sim_backend.repository.CourseRepository;
import com.github.scripting.programming.language.interview_sim_backend.repository.QuestionRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.CourseService;
import com.github.scripting.programming.language.model.CourseCreateRequest;
import com.github.scripting.programming.language.model.CourseDetail;
import com.github.scripting.programming.language.model.CourseDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
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

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        var course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new BaseApiException(HttpStatus.NOT_FOUND, "Такого курса нет"));
        courseRepository.delete(course);
    }

    @Override
    @Transactional
    public CourseDto updateCourse(Long courseId, CourseCreateRequest request) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BaseApiException(HttpStatus.NOT_FOUND, "Такого курса нет"));
        var courseLevel = CourseLevel.findByName(request.getLevel().getValue())
                        .orElseThrow(() -> new BaseApiException(HttpStatus.BAD_REQUEST, "Такого уровня курса не существует"));

        updateCourseFields(request, course, courseLevel);

        var savedCourse = courseRepository.save(course);
        return courseMapper.toCourseDto(savedCourse);
    }

    private void updateCourseFields(CourseCreateRequest request, Course course, CourseLevel courseLevel) {
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setLevel(courseLevel);
        if (request.getCategoryIds() != null) {
            Set<Category> categories = request.getCategoryIds().stream()
                    .map(categoryRepository::getReferenceById)
                    .collect(Collectors.toSet());
            course.setCategories(categories);
        }
        if (request.getQuestionIds() != null) {
            Set<Question> questions = request.getQuestionIds().stream()
                    .map(questionRepository::getReferenceById)
                    .collect(Collectors.toSet());
            course.setQuestions(questions);
        }
    }

    @Override
    @Transactional
    public CourseDto createCourse(CourseCreateRequest courseCreateRequest) {
        var course = new Course();
        var courseLevel = CourseLevel.findByName(courseCreateRequest.getLevel().getValue())
                .orElseThrow(() -> new BaseApiException(HttpStatus.BAD_REQUEST, "Такого уровня курса не существует"));

        updateCourseFields(courseCreateRequest, course, courseLevel);

        var savedCourse = courseRepository.save(course);
        return courseMapper.toCourseDto(savedCourse);
    }
}
