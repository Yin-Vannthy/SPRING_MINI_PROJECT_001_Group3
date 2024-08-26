package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.response.BookmarkResponse;
import com.api.miniproject.miniproject.repository.ArticleRepository;
import com.api.miniproject.miniproject.repository.BookmarkRepository;
import com.api.miniproject.miniproject.repository.UserRepository;
import com.api.miniproject.miniproject.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public Bookmark bookmark(Long articleId) {
        Long userId = CurrentUser.getCurrentUser().getUserId();
        System.out.println("Id: " + userId);

        AppUser appUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        Bookmark bookmark = new Bookmark();
        bookmark.setArticle(article);
        bookmark.setStatus(true);
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUpdatedAt(LocalDateTime.now());
        bookmark.setUser(appUser);

        return bookmarkRepository.save(bookmark);
    }

    @Override
    public List<BookmarkResponse> getBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        return bookmarks.stream().map(Bookmark::toResponse).toList();
    }

}
