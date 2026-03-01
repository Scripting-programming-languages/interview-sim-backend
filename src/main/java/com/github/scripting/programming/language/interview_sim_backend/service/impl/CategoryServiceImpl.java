package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.interview_sim_backend.entity.Category;
import com.github.scripting.programming.language.interview_sim_backend.mapper.CategoryMapper;
import com.github.scripting.programming.language.interview_sim_backend.repository.CategoryRepository;
import com.github.scripting.programming.language.interview_sim_backend.service.CategoryService;
import com.github.scripting.programming.language.model.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAllCategory() {
        // TODO: add pagination
        List<Category> categoryEntityList = categoryRepository.findAll();
        return categoryEntityList.stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }
}
