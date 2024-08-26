package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentByCommentIdAndUserUserId(Long commentId, Long userId);
}
