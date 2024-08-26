package com.api.miniproject.miniproject.model.entity;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.dto.CategoryDto;
import com.api.miniproject.miniproject.model.request.CategoryRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name")
    private String name;
    private Long amountOfArticles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private AppUser user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryArticle> categoriesArticles;


    public Category(Long categoryId, String categoryName, Long amountOfArticles, LocalDateTime createdAt, LocalDateTime updatedAt, AppUser currentUser) {
        this.categoryId = categoryId;
        this.name = categoryName;
        this.amountOfArticles = amountOfArticles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = currentUser;
    }

    public Category(Long categoryId, String categoryName, Long amountOfArticles, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.name = categoryName;
        this.amountOfArticles = amountOfArticles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CategoryDto toCategoryResponse() {
        return new CategoryDto(this.categoryId, this.name, this.amountOfArticles, this.createdAt, this.updatedAt);
    }

    public CategoryDto toCategoryResponse(List<ArticleDto> articles) {
        return new CategoryDto(this.categoryId, this.name, this.amountOfArticles, this.createdAt, this.updatedAt, articles);
    }

    public void updateCategoryEntity(CategoryRequest categoryRequest, AppUser user) {
        this.name = categoryRequest.getCategoryName();
        this.updatedAt = LocalDateTime.now();
        this.user = user;
    }
}
