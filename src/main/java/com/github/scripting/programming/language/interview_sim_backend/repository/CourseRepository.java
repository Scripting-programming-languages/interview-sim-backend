package com.github.scripting.programming.language.interview_sim_backend.repository;

import com.github.scripting.programming.language.interview_sim_backend.entity.Course;
import com.github.scripting.programming.language.interview_sim_backend.entity.CourseLevel;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
        SELECT DISTINCT c FROM Course c
        LEFT JOIN FETCH c.categories cats
        WHERE (:categoryId IS NULL OR EXISTS (
            SELECT 1 FROM c.categories sub_cats WHERE sub_cats.id = :categoryId
        ))
        AND (:level IS NULL OR c.level = :level)
    """)
    List<Course> findAllWithCategoryByCategoryIdAndCourseLevel(
            @Nullable @Param("categoryId") Long categoryId,
            @Nullable @Param("level") CourseLevel level
    );

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.categories WHERE c.id = :id")
    Optional<Course> findWithCategoriesById(@Param("id") Long id);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.questions WHERE c.id = :id")
    Optional<Course> findWithQuestionsById(@Param("id") Long id);
}
