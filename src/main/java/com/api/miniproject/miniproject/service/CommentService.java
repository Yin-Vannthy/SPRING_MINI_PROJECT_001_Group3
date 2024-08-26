package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.CommentDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import jakarta.validation.Valid;

public interface CommentService {
    void createComment(CommentRequest commentRequest, Article article, AppUser appUser);

    CommentDto getComment(Long commentId);

    CommentDto updateComment(Long commentId, @Valid CommentRequest commentRequest);

    void deleteComment(Long commentId);
}
