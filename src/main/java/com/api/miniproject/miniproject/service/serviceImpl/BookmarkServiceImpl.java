package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.dto.BookmarkDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.entity.Bookmark;
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
    public List<ArticleDto> getBookmarks(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> articles = articleRepository.findAll(pageable);

        return articles.getContent().stream()
                .filter(article -> article.getBookmarks().stream().anyMatch(Bookmark::getStatus))
                .map(Article::toArticleResponseWithCategoryIds)
                .collect(Collectors.toList());
    }

    @Override
    public BookmarkDto postBookmark(Long articleId) {
        // Get the current user's ID (assuming CurrentUser class handles JWT or authentication)
        Long userId = CurrentUser.getCurrentUser().getUserId();

        // Fetch the user entity from the database
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // Fetch the article entity from the database
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // Check if the article is already bookmarked by the user
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByArticleAndUser(article, appUser);

        if (existingBookmark.isPresent()) {
            Bookmark bookmark = existingBookmark.get();

            // Reactivate the bookmark if it was previously inactive
            if (!bookmark.getStatus()) {
                bookmark.setStatus(true);  // Reactivate bookmark
                bookmark.setUpdatedAt(LocalDateTime.now());
                return bookmarkRepository.save(bookmark).toResponse();
            } else {
                // Bookmark is already active, return existing bookmark
                return bookmark.toResponse();
            }
        }

        // If no bookmark exists, create a new one using the toEntity method from BookmarkDto
        BookmarkDto bookmarkDTO = new BookmarkDto();
        bookmarkDTO.setArticleId(articleId);
        bookmarkDTO.setStatus(true);  // Set the status as active

        // Convert DTO to Bookmark entity using toEntity()
        Bookmark bookmark = bookmarkDTO.toEntity(article, appUser);

        // Save the bookmark and return it
        return bookmarkRepository.save(bookmark).toResponse();
    }


    @Override
    public BookmarkDto updateBookmark(Long articleId) {
        Long userId = CurrentUser.getCurrentUser().getUserId();

        // Fetch the user entity from the database
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // Fetch the article entity from the database
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid article ID"));

        // Check if the article is already bookmarked by the user
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByArticleAndUser(article, appUser);

        if (existingBookmark.isEmpty()) {
            throw new IllegalArgumentException("Bookmark does not exist for this article and user.");
        }

        Bookmark bookmark = existingBookmark.get();

        // Update the bookmark details as needed
        // For example, you can toggle the status
        bookmark.setStatus(!bookmark.getStatus());
        bookmark.setUpdatedAt(LocalDateTime.now());

        // Save the updated bookmark
        Bookmark updatedBookmark = bookmarkRepository.save(bookmark);

        // Convert the updated bookmark to BookmarkDto
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setArticleId(updatedBookmark.getArticle().getArticleId());
        bookmarkDto.setStatus(updatedBookmark.getStatus());

        return bookmarkDto;
    }



}
