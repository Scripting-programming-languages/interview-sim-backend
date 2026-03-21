package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.dto.AnswerEstimationMsg;
import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;

public interface AnswerService {
    Answer save(Attempt attempt, Question question);

    Answer updateAnswerByEstimation(AnswerEstimationMsg answerEstimationMsg);

    boolean existAnswer(Attempt attempt, Question question);
}
