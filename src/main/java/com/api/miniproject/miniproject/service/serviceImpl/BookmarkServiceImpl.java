package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.dto.BookmarkDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.repository.ArticleRepository;
import com.api.miniproject.miniproject.repository.BookmarkRepository;
import com.api.miniproject.miniproject.repository.UserRepository;
import com.api.miniproject.miniproject.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final UserRepository appUserRepository;
    private final ArticleRepository articleRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public List<ArticleDto> getBookmarks(Integer pageNo, Integer pageSize, Enums.Article sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy.name());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> articles = articleRepository.findAll(pageable);

        return articles.getContent().stream()
                .filter(article -> article.getBookmarks().stream().anyMatch(Bookmark::getStatus))
                .map(Article::toArticleResponseWithRelatedData)
                .collect(Collectors.toList());
    }

    @Override
    public String postBookmark(Long articleId) {
        // Fetch the user entity from the database
        AppUser appUser = appUserRepository.findById(CurrentUser.getCurrentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("Invalid user ID"));

        // Fetch the article entity from the database
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomNotFoundException("No article with id: " + articleId + " found."));

        // Check if the article is already bookmarked by the user
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByArticleAndUser(article, appUser);

        if (existingBookmark.isPresent()) {
            Bookmark bookmark = existingBookmark.get();

            // If the bookmark is active, return a message indicating it's already bookmarked
            if (bookmark.getStatus()) {
                return "Article is already bookmarked.";
            }

            // If the bookmark was inactive, reactivate it
            bookmark.setStatus(true);
            bookmark.setUpdatedAt(LocalDateTime.now());
            bookmarkRepository.save(bookmark);

            return "An article id " + articleId + " already bookmarked!";
        }

        // If no bookmark exists, create a new one using BookmarkDto
        BookmarkDto bookmarkDTO = new BookmarkDto(articleId, true);  // Active by default

        // Convert DTO to entity
        Bookmark newBookmark = bookmarkDTO.toEntity(article, appUser);
        newBookmark.setCreatedAt(LocalDateTime.now());
        newBookmark.setUpdatedAt(LocalDateTime.now());

        // Save the new bookmark
        bookmarkRepository.save(newBookmark);

        // Return success message
        return "An article id " + articleId + " is bookmarked successfully.";
    }

    @Override
    public String updateBookmark(Long articleId) {
        // Fetch the current user's ID
        Long userId = CurrentUser.getCurrentUser().getUserId();

        // Fetch the user entity from the database
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("Invalid user ID: " + userId));

        // Fetch the article entity from the database
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new CustomNotFoundException("No article with id: " + articleId + " found."));

        // Fetch the existing bookmark (or throw if not found)
        Bookmark bookmark = bookmarkRepository.findByArticleAndUser(article, appUser)
                .orElseThrow(() -> new CustomNotFoundException("Bookmark does not exist for this article and user."));

        // Check if the current status is false (inactive)
        if (!bookmark.getStatus()) {
            // If the bookmark is already inactive, return without making any changes
            return "An Article id " + articleId + " is already unmark.";
        }

        // If the bookmark is active, change its status to inactive (false)
        bookmark.setStatus(false);
        bookmark.setUpdatedAt(LocalDateTime.now());

        // Save the updated bookmark
        bookmarkRepository.save(bookmark);

        // Return success message
        return "An article id " + articleId + " is unmarked successfully.";
    }
}
