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
            return existingBookmark.get().toResponse();  // Return the existing bookmark if already present
        }

        // If no bookmark exists, create a new one using the toEntity method from BookmarkDTO
        BookmarkDto bookmarkDTO = new BookmarkDto();
        bookmarkDTO.setArticleId(articleId);
        bookmarkDTO.setStatus(true);  // Set the status as active, or handle dynamically as needed

        // Convert DTO to Bookmark entity using toEntity()
        Bookmark bookmark = bookmarkDTO.toEntity(article, appUser);

        // Save the bookmark and return it
        return bookmarkRepository.save(bookmark).toResponse();

    }



}
