package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.CommentDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
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

    private AppUser currentUser() {
        return CurrentUser.getCurrentUser();
    }

    @Override
    public void createComment(CommentRequest commentRequest, Article article, AppUser appUser) {
        commentRepository.save(new Comment(commentRequest, article, appUser)).toCommentResponse();
    }

    @Override
    public CommentDto getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomNotFoundException("No comment with Id : " + commentId + " was found."))
                .toCommentResponse();
    }

    @Override
    public CommentDto updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository
                .findCommentByCommentIdAndUserUserId(commentId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("You are not the owner of the comment with Id : " + commentId));

        comment.setCmt(commentRequest.getComment().trim());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment).toCommentResponse();
    }

    @Override
    public String deleteComment(Long commentId) {
        commentRepository
                .findCommentByCommentIdAndUserUserId(commentId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("You are not the owner of the comment with Id : " + commentId));
        commentRepository.deleteById(commentId);

        return "Comment with Id : " + commentId + " was deleted successfully.";
    }
}
