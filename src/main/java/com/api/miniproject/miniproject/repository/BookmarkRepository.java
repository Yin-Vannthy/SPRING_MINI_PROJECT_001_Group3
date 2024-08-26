package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findBookmarkByArticleArticleIdAndUserUserId(Long articleId, Long userId);
}
