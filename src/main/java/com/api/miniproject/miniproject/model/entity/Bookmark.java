package com.api.miniproject.miniproject.model.entity;

import com.api.miniproject.miniproject.model.response.BookmarkResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "article_id", referencedColumnName = "articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private AppUser user;

    public BookmarkResponse toResponse(){
        return new BookmarkResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                this.createdAt,
                user.getUserId(),
                null
        );
    }

}
