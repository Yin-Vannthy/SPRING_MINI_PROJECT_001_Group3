package com.api.miniproject.miniproject.model.dto;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long amountOfArticles;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArticleDto> articleList = new ArrayList<>();

    public CategoryDto(Long categoryId, String name, Long amountOfArticles, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.categoryName = name;
        this.amountOfArticles = amountOfArticles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CategoryDto(Long categoryId, String name, Long amountOfArticles, LocalDateTime createdAt, LocalDateTime updatedAt, List<ArticleDto> articles) {
        this.categoryId = categoryId;
        this.categoryName = name;
        this.amountOfArticles = amountOfArticles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.articleList = articles;
    }

    public Category toCategoryEntity() {
        return new Category(this.categoryId, this.categoryName, this.amountOfArticles, this.createdAt, this.updatedAt);
    }

    public Category toCategoryEntity(AppUser user) {
        return new Category(this.categoryId, this.categoryName, this.amountOfArticles, this.createdAt, this.updatedAt, user);
    }
}
