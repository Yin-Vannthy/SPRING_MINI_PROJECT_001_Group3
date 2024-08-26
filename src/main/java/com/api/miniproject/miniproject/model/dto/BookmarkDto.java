package com.api.miniproject.miniproject.model.dto;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto {
    private Long articleId;
    private Boolean status;

    public Bookmark toEntity(Article article, AppUser user) {
        Bookmark bookmark = new Bookmark();
        bookmark.setArticle(article);
        bookmark.setUser(user);
        bookmark.setStatus(this.status != null ? this.status : true);  // Default status to true if null
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUpdatedAt(LocalDateTime.now());
        return bookmark;
    }
}

