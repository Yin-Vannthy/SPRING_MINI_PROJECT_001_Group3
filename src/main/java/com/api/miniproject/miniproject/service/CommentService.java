package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.response.CommentResponse;

public interface CommentService {
    CommentResponse getCommentById(Long id);

    void deleteCommentById(Long id);

    CommentResponse updateCommentById(Long id, CommentRequest commentRequest);
}
