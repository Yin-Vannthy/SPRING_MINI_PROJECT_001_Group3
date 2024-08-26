package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCaseAndUserUserId(String name, Long userId);
    Optional<Category> findByCategoryIdAndUserUserId(Long categoryId, Long userId);
}
