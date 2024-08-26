package com.api.miniproject.miniproject.model.response;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String cmt;
    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private Article article;
    private AppUser user;
}
