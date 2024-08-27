package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.ArticleRequest;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleRequest articleRequest);

    ArticleDto getArticle(Long articleId);

    List<ArticleDto> findArticlesByCategoryCategoryId(Long categoryId);

    void deleteArticle(Long articleId);

    ArticleDto updateArticle(Long articleId, ArticleRequest articleRequest);

    List<ArticleDto> getAllArticles(Integer pageNo, Integer pageSize, Enums.Article sortBy, Sort.Direction sortDirection);

    ArticleDto createComment(Long articleId, @Valid CommentRequest commentRequest);

    ArticleDto getComments(Long articleId);

    List<Article> findAllByUserId(Pageable pageable, Long userId);
}
