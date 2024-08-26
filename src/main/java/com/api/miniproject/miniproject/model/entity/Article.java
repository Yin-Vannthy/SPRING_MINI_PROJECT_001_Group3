package com.api.miniproject.miniproject.model.entity;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.request.ArticleRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private AppUser user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryArticle> categoriesArticles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "article", orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "article")
    private List<Bookmark> bookmarks;

    public Article(ArticleRequest articleRequest, AppUser user) {
        this.title = articleRequest.getTitle();
        this.description = articleRequest.getDescription();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.user = user;
    }

    public Article(Long articleId, LocalDateTime createdAt, String title, String description, AppUser user) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = LocalDateTime.now();
        this.user = user;
    }

    public ArticleDto toArticleResponseWithCategoryIds() {
        return new ArticleDto(
                this.articleId,
                this.title,
                this.description,
                this.createdAt,
                this.updatedAt,
                this.user,
                this.categoriesArticles.stream()
                        .map(c -> c.getCategory().toCategoryResponse().getCategoryId())
                        .collect(Collectors.toList()));
    }
}
