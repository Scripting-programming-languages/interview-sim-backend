package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEstimationMsg;
import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import com.github.scripting.programming.language.interview_sim_backend.entity.AnswerStatus;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import com.github.scripting.programming.language.interview_sim_backend.exception.EntityNotExist;
import com.github.scripting.programming.language.interview_sim_backend.repository.AnswerRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;


    @Override
    @Transactional
    public Answer save(Attempt attempt, Question question) {
        var answer = Answer.builder()
                .attempt(attempt)
                .question(question)
                .status(AnswerStatus.ESTIMATING)
                .build();
        return answerRepository.save(answer);
    }

    @Override
    @Transactional
    public Answer updateAnswerByEstimation(AnswerEstimationMsg answerEstimationMsg) {
        var answer = answerRepository.findById(answerEstimationMsg.answerId())
                .orElseThrow(entityNotExistSupplier(answerEstimationMsg.answerId()));

        answer.setUserAnswer(answerEstimationMsg.transcribedText());
        answer.setAnswerScore(answerEstimationMsg.score());
        answer.setAnswerFeedback(answerEstimationMsg.textFeedback());
        answer.setSpeechScore(answerEstimationMsg.speechScore());
        answer.setSpeechFeedback(answerEstimationMsg.speechFeedback());

        return answerRepository.save(answer);
    }

    private static Supplier<EntityNotExist> entityNotExistSupplier(Long answerId) {
        return () -> new EntityNotExist("Answer with id {" + answerId + "} not exist");
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existAnswer(Attempt attempt, Question question) {
        return answerRepository.findByAttemptAndQuestion(attempt, question)
                .isPresent();
    }
}
