package com.github.scripting.programming.language.interview_sim_backend.mapper;

import com.github.scripting.programming.language.interview_sim_backend.entity.Answer;
import com.github.scripting.programming.language.model.UserAnswerResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DateMapper.class}
)
public interface AnswerMapper {
    @Mapping(target = "questionId", source = "question.id")
    UserAnswerResult toUserAnswerResult(Answer answer);
}
