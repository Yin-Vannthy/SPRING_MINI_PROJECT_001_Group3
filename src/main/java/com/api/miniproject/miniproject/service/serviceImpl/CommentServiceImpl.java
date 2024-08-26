package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.CommentDto;
import com.api.miniproject.miniproject.model.entity.Comment;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.repository.CommentRepository;
import com.api.miniproject.miniproject.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;


    @Override
    public CommentDto getCommentById(Long commentId) {
        Long userId = CurrentUser.getCurrentUser().getUserId();
        Comment comment = commentRepository.getCommentById(commentId, userId);
        if (comment == null) {
            throw new CustomNotFoundException("Cannot find this comment!");
        }
        return comment.toResponse();
    }

    @Override
    public void deleteCommentById(Long commentId) {
        Long userId = CurrentUser.getCurrentUser().getUserId();
        Comment comment = commentRepository.getCommentById(commentId, userId);
        if (comment == null) {
            throw new CustomNotFoundException("Cannot find this comment!");
        }
        System.out.println(comment);
        commentRepository.delete(comment);
        System.out.println(comment);
    }

    @Override
    public CommentDto updateCommentById(Long commentId, CommentRequest commentRequest) {
        Long userId = CurrentUser.getCurrentUser().getUserId();
        Comment commentToUpdate = commentRepository.getCommentById(commentId, userId);
        if (commentToUpdate == null) {
            throw new CustomNotFoundException("Cannot find this comment!");
        }

        commentToUpdate.setCommentId(commentId);
        commentToUpdate.setCmt(commentRequest.getComment());
        commentToUpdate.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(commentToUpdate).toResponse();

    }

}
