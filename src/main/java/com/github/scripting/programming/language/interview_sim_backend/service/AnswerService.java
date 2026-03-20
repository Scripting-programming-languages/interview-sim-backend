package com.github.scripting.programming.language.interview_sim_backend.service;

import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.interview_sim_backend.entity.Question;

public interface AnswerService {
    Answer save(Attempt attempt, Question question);

    boolean existAnswer(Attempt attempt, Question question);
}
