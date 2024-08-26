package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.Article;
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
                        SELECT a.* FROM article a JOIN category_article ca
                                   ON a.article_id = ca.article_id
                                   JOIN category c ON ca.category_id = c.category_id
                        WHERE ca.category_id = :categoryId
                    """,
            nativeQuery = true
    )
    List<Article> findArticlesByCategoryCategoryId(@Param("categoryId") Long categoryId);

    Optional<Article> findArticleByArticleIdAndUserUserId(Long articleId, Long userId);

    void deleteArticleByArticleIdAndUserUserId(Long articleId, Long userId);
}
