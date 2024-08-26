package com.api.miniproject.miniproject.repository;

import com.api.miniproject.miniproject.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
@Query("SELECT c FROM Comment c WHERE c.commentId = :commentId AND c.user.userId = :userId")
    Comment getCommentById(@Param("commentId") Long commentId, @Param("userId") Long userId);

}
