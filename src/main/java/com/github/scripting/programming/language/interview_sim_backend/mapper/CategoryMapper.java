package com.github.scripting.programming.language.interview_sim_backend.mapper;

import com.github.scripting.programming.language.interview_sim_backend.entity.Category;
import com.github.scripting.programming.language.model.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
}
