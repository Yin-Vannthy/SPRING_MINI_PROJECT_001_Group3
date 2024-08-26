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
    @Query(
            value = """
                        SELECT a FROM Article a JOIN CategoryArticle ca
                                   ON a.articleId = ca.article.articleId
                                   JOIN Category c ON ca.category.categoryId = c.categoryId
                        WHERE c.categoryId = :categoryId
                    """
    )
    List<Article> findArticlesByCategoryCategoryId(@Param("categoryId") Long categoryId);

    Optional<Article> findArticleByArticleIdAndUserUserId(Long articleId, Long userId);

    void deleteArticleByArticleIdAndUserUserId(Long articleId, Long userId);

    @Query(value = """
                       SELECT a FROM Article a JOIN Bookmark b ON a.articleId = b.article.articleId 
                       WHERE b.user.userId = :userId AND b.status = TRUE
                   """
    )
    Page<Article> findAllArticlesByBookmark(Pageable pageable, @Param("userId") Long userId);

    Optional<Article> findArticleByTitleAndUserUserId(String title, Long userId);
}
