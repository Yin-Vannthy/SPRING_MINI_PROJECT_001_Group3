package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryArticleRepository extends JpaRepository<CategoryArticle, Long> {
    Long countByCategoryCategoryId(Long categoryId);
    void deleteByArticleArticleId(Long articleId);
}
