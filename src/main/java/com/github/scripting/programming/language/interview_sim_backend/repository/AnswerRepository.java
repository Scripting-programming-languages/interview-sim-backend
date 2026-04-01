package com.github.scripting.programming.language.interview_sim_backend.repository;

import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByAttemptAndQuestion(Attempt attempt, Question question);

    List<Answer> findAllByAttempt(Attempt attempt);
}
