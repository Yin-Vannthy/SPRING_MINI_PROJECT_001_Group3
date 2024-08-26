package com.api.miniproject.miniproject.model.entity;

import com.api.miniproject.miniproject.model.response.CommentResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String cmt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "article_id", referencedColumnName = "articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private AppUser user;


    public CommentResponse toResponse(){
        return new CommentResponse(commentId, cmt, createdAt, user);
    }
}
