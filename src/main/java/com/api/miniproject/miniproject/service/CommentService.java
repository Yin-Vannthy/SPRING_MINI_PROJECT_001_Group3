package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.dto.CommentDto;

public interface CommentService {
    CommentDto getCommentById(Long id);

    void deleteCommentById(Long id);

    CommentDto updateCommentById(Long id, CommentRequest commentRequest);

    void createComment(CommentRequest commentRequest, Article article, AppUser appUser);
}
