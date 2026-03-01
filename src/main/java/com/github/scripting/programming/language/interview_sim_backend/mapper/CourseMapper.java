package com.github.scripting.programming.language.interview_sim_backend.mapper;

import com.github.scripting.programming.language.interview_sim_backend.entity.Course;
import com.github.scripting.programming.language.interview_sim_backend.entity.CourseLevel;
import com.github.scripting.programming.language.model.CourseDetail;
import com.github.scripting.programming.language.model.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, QuestionMapper.class, DateMapper.class} // to map Category to CategoryDto and Question to QuestionDto
)
public interface CourseMapper {
    @Mapping(source = "level", target = "level") // use method map below
    CourseDto toCourseDto(Course course);

    CourseDto.LevelEnum mapToCourseDtoLevelEnum(CourseLevel value);

    @Mapping(source = "level", target = "level")
    CourseDetail toCourseDetail(Course course);

    CourseDetail.LevelEnum mapToCourseDetailLevelEnum(CourseLevel value);
}
