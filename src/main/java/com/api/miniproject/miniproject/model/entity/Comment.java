package com.api.miniproject.miniproject.model.entity;

import com.api.miniproject.miniproject.model.dto.CommentDto;
import com.api.miniproject.miniproject.model.dto.UserDto;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "article_id", referencedColumnName = "articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private AppUser user;

    public CommentDto toResponse(){
        UserDto userDto = user.toUserResponse();
        return new CommentDto(commentId, cmt, createdAt, userDto);
    }
}
