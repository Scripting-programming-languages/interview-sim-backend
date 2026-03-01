package com.github.scripting.programming.language.interview_sim_backend.mapper;

import com.github.scripting.programming.language.interview_sim_backend.entity.Attempt;
import com.github.scripting.programming.language.model.AttemptDetail;
import com.github.scripting.programming.language.model.AttemptStartResponse;
import com.github.scripting.programming.language.model.AttemptSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {QuestionMapper.class, AnswerMapper.class, DateMapper.class}
)
public interface AttemptMapper {
    @Mapping(target = "attemptId", source = "id")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "questions", source = "course.questions")
    AttemptStartResponse toAttemptStartResponse(Attempt attempt);

    @Mapping(target = "attemptId", source = "id")
    @Mapping(target = "courseId", source = "course.id")
    AttemptDetail toAttemptDetail(Attempt attempt);

    @Mapping(target = "attemptId", source = "id")
    @Mapping(target = "courseId", source = "course.id")
    AttemptSummary toAttemptSummary(Attempt attempt);
}
