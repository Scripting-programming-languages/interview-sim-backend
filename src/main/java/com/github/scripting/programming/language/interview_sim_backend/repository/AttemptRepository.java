package com.github.scripting.programming.language.interview_sim_backend.repository;

import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    @Query("""
                SELECT att FROM Attempt att
                LEFT JOIN FETCH att.answers ans
                WHERE att.id = :id AND att.user.id = :userId
            """)
    Optional<Attempt> findWithAnswersByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("""
                SELECT att FROM Attempt att
                LEFT JOIN FETCH att.course c
                WHERE att.id = :id AND att.user.id = :userId
            """)
    Optional<Attempt> findWithCourseByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("""
                SELECT DISTINCT att FROM Attempt att
                LEFT JOIN FETCH att.course c
                WHERE att.user.id = :userId
            """)
    List<Attempt> findAllWithCourseByUserId(@Param("userId") Long userId);
}
