package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.dto.CategoryDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Category;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.CategoryRequest;
import com.api.miniproject.miniproject.repository.CategoryRepository;
import com.api.miniproject.miniproject.service.ArticleService;
import com.api.miniproject.miniproject.service.CategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ArticleService articleService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, @Lazy ArticleService articleService) {
        this.categoryRepository = categoryRepository;
        this.articleService = articleService;
    }

    private AppUser currentUser() {
        return CurrentUser.getCurrentUser();
    }

    @Override
    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        if (getCategoryByName(categoryRequest.getCategoryName().trim()).isPresent()) {
            throw new CustomNotFoundException("Category with name: " + categoryRequest.getCategoryName().trim() + " is already in use.");
        }
        return categoryRepository.save(categoryRequest.toCategoryEntity(currentUser())).toCategoryResponse();
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findByCategoryIdAndUserUserId(categoryId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("No category with Id : " + categoryId + " was found."));
        return category.toCategoryResponse(getArticlesByCategoryId(categoryId));
    }

    @Override
    public CategoryDto updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository.findByCategoryIdAndUserUserId(categoryId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("No category with Id : " + categoryId + " was found."));

        if (getCategoryByName(categoryRequest.getCategoryName().trim()).isPresent() && !category.getCategoryId().equals(categoryId)) {
            throw new CustomNotFoundException("Category with name: " + categoryRequest.getCategoryName().trim() + " is already in use.");
        }

        category.updateCategoryEntity(categoryRequest, currentUser());

        return categoryRepository.save(category).toCategoryResponse(getArticlesByCategoryId(categoryId));
    }

    @Override
    public List<CategoryDto> getCategories(Integer pageNo, Integer pageSize, Enums.Category sortBy, Sort.Direction sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection, sortBy.name()));
        List<Category> categories = categoryRepository.findAll(pageable).getContent();

        return categories.stream()
                .map(category -> category.toCategoryResponse(getArticlesByCategoryId(category.getCategoryId())))
                .toList();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findByCategoryIdAndUserUserId(categoryId, currentUser().getUserId()).orElseThrow(
                () -> new CustomNotFoundException("No category with Id : " + categoryId + " was found.")
        );
        categoryRepository.deleteById(category.getCategoryId());
    }

    @Override
    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryRepository.findByNameIgnoreCaseAndUserUserId(categoryName, currentUser().getUserId());
    }

    private List<ArticleDto> getArticlesByCategoryId(Long categoryId) {
        return articleService.findArticlesByCategoryCategoryId(categoryId);
    }
}
