package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("""
        SELECT a FROM Article a JOIN CategoryArticle ca
                   ON a.articleId = ca.article.articleId
                   JOIN Category c ON ca.category.categoryId = c.categoryId
        WHERE c.categoryId = :categoryId
    """)
    List<Article> findArticlesByCategoryCategoryId(@Param("categoryId") Long categoryId);

    Optional<Article> findArticleByArticleIdAndUserUserId(Long articleId, Long userId);

    void deleteArticleByArticleIdAndUserUserId(Long articleId, Long userId);

    Optional<Article> findArticleByTitleAndUserUserId(String title, Long userId);

    @Query("""
        SELECT a FROM Article a JOIN Bookmark b ON b.article.articleId = a.articleId
        WHERE b.user.userId = :userId AND b.status = TRUE
    """)
    Page<Article> findArticlesByUserId(@Param("userId") Long userId, Pageable pageable);
}