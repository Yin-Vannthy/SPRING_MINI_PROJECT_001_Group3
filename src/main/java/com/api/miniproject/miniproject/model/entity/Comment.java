package com.api.miniproject.miniproject.model.entity;

import com.api.miniproject.miniproject.model.dto.CommentDto;
import com.api.miniproject.miniproject.model.dto.UserDto;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String cmt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "article_id", referencedColumnName = "articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private AppUser user;

    public Comment(CommentRequest commentRequest, Article article, AppUser appUser) {
        this.cmt = commentRequest.getComment().trim();
        this.createdAt = LocalDateTime.now();
        this.article = article;
        this.user = appUser;
    }

    public CommentDto toCommentResponse() {
        return new CommentDto(this.commentId, this.cmt.trim(), this.createdAt, this.updatedAt, this.user);
    }

    public CommentDto toResponse(){
        UserDto userDto = user.toUserResponse();
        return new CommentDto(commentId, cmt, createdAt, updatedAt, userDto);
    }
}
