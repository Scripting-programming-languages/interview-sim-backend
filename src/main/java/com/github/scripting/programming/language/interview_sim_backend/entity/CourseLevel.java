package com.github.scripting.programming.language.interview_sim_backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum CourseLevel {
    JUNIOR("junior"),
    MIDDLE("middle"),
    SENIOR("senior");

    private final String name;

    private static final CourseLevel[] COURSE_LEVELS = CourseLevel.values();

    public static Optional<CourseLevel> findByName(String value) {
        for (var course: COURSE_LEVELS) {
            if (course.name.equalsIgnoreCase(value)) {
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }
}
