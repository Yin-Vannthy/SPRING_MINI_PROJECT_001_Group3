package com.api.miniproject.miniproject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CommentRequest {
    @NotEmpty(message = "Comment Can not be empty")
    @NotBlank(message = "Comment Can not be blank")
    @NotNull(message = "Comment Can not be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Comment allow only character and number")
    private String comment;
}
