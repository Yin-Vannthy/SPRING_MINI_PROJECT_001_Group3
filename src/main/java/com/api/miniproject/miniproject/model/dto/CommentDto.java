package com.api.miniproject.miniproject.model.dto;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Long commentId;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;

    public CommentDto(Long commentId, String cmt, LocalDateTime createdAt, LocalDateTime updatedAt, AppUser user) {
        this.commentId = commentId;
        this.comment = cmt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user.toUserResponse();
    }

    public CommentDto(Long commentId, String cmt, LocalDateTime createdAt, UserDto userDto) {
        this.commentId = commentId;
        this.comment = cmt;
        this.createdAt = createdAt;
        this.user = userDto;
    }
}
