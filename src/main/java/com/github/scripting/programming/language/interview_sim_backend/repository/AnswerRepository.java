package com.github.scripting.programming.language.interview_sim_backend.repository;

import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
