package com.api.miniproject.miniproject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @NotNull(message = "Comment should not be null")
    @NotBlank(message = "Comment should not be blank")
    private String comment;

}
