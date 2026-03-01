package com.github.scripting.programming.language.interview_sim_backend.repository;

import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("""
            SELECT q
            FROM Question q
            JOIN q.courses c
            WHERE q.id = :questionId AND c.id = :courseId"""
    )
    Optional<Question> findQuestionByCourseIdAndQuestionId(
            @Param("questionId") Long questionId,
            @Param("courseId") Long courseId
    );
}
