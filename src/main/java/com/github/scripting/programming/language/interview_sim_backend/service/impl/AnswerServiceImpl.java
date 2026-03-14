package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import com.github.scripting.programming.language.interview_sim_backend.repository.AnswerRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    @Transactional
    public Answer save(Attempt attempt, Question question, String userAnswer, Integer score, String feedback) {
        var answer = Answer.builder()
                .attempt(attempt)
                .question(question)
                .userAnswer(userAnswer)
                .feedback(feedback)
                .score(score)
                .build();
        return answerRepository.save(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existAnswer(Attempt attempt, Question question) {
        return answerRepository.findByAttemptAndQuestion(attempt, question)
                .isPresent();
    }
}
