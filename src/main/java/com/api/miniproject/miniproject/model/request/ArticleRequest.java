package com.api.miniproject.miniproject.model.request;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ArticleRequest {
    @NotEmpty(message = "Title Can not be empty")
    @NotBlank(message = "Title Can not be blank")
    @NotNull(message = "Title Can not be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Title allow only character and number")
    private String title;

    @NotEmpty(message = "Description Can not be empty")
    @NotBlank(message = "Description Can not be blank")
    @NotNull(message = "Description Can not be null")
    private String description;

    @NotEmpty(message = "Category Id Can not be empty")
    @NotNull(message = "Category Id Can not be null")
    private List<Long> categoriesId;

    public Article toArticleEntity(Article article, AppUser user) {
        return new Article(article.getArticleId(),article.getCreatedAt(), this.title.trim(), this.description.trim(), user);
    }
}
