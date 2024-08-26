package com.api.miniproject.miniproject.model.request;

import com.api.miniproject.miniproject.model.entity.Comment;

public class CommentRequest {
    private String comment;

    public Comment toEntity(Long id){
        return new Comment(id, comment, null, null, null, null);
    }
}
