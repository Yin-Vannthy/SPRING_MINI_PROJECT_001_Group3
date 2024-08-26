package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.model.entity.Comment;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.response.CommentResponse;
import com.api.miniproject.miniproject.repository.CommentRepository;
import com.api.miniproject.miniproject.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    @Override
    public CommentResponse getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment.toResponse();
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentResponse updateCommentById(Long id, CommentRequest commentRequest) {
        Comment commentToUpdate = commentRepository.findById(id).orElse(null);
        Comment updatedComment = commentRequest.toEntity(id);
        return updatedComment.toResponse();
    }
}
