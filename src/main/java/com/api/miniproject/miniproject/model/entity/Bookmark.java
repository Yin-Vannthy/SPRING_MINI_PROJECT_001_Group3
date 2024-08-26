package com.api.miniproject.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
}
