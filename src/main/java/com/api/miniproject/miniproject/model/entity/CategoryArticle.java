package com.api.miniproject.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "article_id", referencedColumnName = "articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;


    public CategoryArticle(Category category, Article article) {
        this.article = article;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
}
