package com.api.miniproject.miniproject.model.response;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkResponse {
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private Long ownerArticle;
    private List<Long> categoryIdList;
}
