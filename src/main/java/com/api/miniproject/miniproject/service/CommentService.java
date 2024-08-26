package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.dto.CommentDto;

public interface CommentService {
    CommentDto getCommentById(Long id);

    void deleteCommentById(Long id);

    CommentDto updateCommentById(Long id, CommentRequest commentRequest);


}
