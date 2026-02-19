package com.github.scripting.programming.language.interview_sim_backend.mapper;

import com.github.scripting.programming.language.interview_sim_backend.entity.Question;
import com.github.scripting.programming.language.model.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "keyWords", ignore = true)
    @Mapping(target = "correctAnswer", ignore = true)
    @Mapping(target = "questionId", source = "id")
    @Mapping(target = "questionType", constant = "OBJECTIVE")
    @Mapping(target = "correctAnswer", ignore = true)
    QuestionDto toQuestionDto(Question question);
}
