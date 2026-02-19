package com.github.scripting.programming.language.interview_sim_backend.controller;

import com.github.scripting.programming.language.controller.CategoriesApi;
import com.github.scripting.programming.language.interview_sim_backend.service.CategoryService;
import com.github.scripting.programming.language.model.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoriesApi {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryDto>> categoriesGet() {
        List<CategoryDto> result = categoryService.getAllCategory();
        return ResponseEntity.ok(result);
    }
}
