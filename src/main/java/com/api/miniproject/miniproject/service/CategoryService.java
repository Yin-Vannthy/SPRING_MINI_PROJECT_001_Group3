package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.CategoryDto;
import com.api.miniproject.miniproject.model.entity.Category;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.CategoryRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto createCategory(CategoryRequest categoryRequest);

    CategoryDto getCategory(Long categoryId);

    CategoryDto updateCategory(CategoryRequest categoryRequest, Long categoryId);

    List<CategoryDto> getCategories(Integer pageNo, Integer pageSize, Enums.Category sortBy, Sort.Direction sortDirection);

    String deleteCategory(Long categoryId);

    Optional<Category> getCategoryByName(String categoryName);
}
