package com.api.miniproject.miniproject.model.dto;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    private Long articleId;
    private String title;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;

    private Long ownerOfArticle;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> categoryIdList;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentDto> commentList;

    public ArticleDto(Long articleId, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, AppUser user, List<Long> categoryIds, List<CommentDto> comments) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerOfArticle = user.getUserId();
        this.categoryIdList = categoryIds;
        this.commentList = comments;
    }

    public Article toArticleEntity(){
        return new Article(this.articleId, this.title, this.description, this.createdAt, this.updatedAt);
    }
}