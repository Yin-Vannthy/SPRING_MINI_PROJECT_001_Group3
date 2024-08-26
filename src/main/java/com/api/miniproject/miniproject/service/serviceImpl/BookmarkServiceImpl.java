package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.configuration.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.repository.BookmarkRepository;
import com.api.miniproject.miniproject.service.ArticleService;
import com.api.miniproject.miniproject.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ArticleService articleService;

    private AppUser currentUser() {
        return CurrentUser.getCurrentUser();
    }

    @Override
    public String createBookmark(Long articleId) {
        bookmarkRepository.save(new Bookmark(articleService.getArticle(articleId).toArticleEntity(), currentUser()));
        return "An article with Id : " + articleId + " is bookmarked successfully.";
    }

    @Override
    public String updateBookmark(Long articleId) {
        Bookmark bookmark = bookmarkRepository.findBookmarkByArticleArticleIdAndUserUserId(articleId, currentUser().getUserId())
                .orElseThrow(() -> new CustomNotFoundException("No article with Id : " + articleId + " was found."));

        bookmark.setStatus(!bookmark.getStatus());
        bookmark.setUpdatedAt(LocalDateTime.now());
        bookmarkRepository.save(bookmark);

        return bookmark.getStatus() ?
                "An article with Id : " + articleId + " is bookmarked successfully." :
                "An article with Id : " + articleId + " is unmarked successfully.";
    }

    @Override
    public List<ArticleDto> getBookmarks(Integer pageNo, Integer pageSize, Enums.Article sortBy, Sort.Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy.name());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return articleService.findAllArticlesByBookmark(pageable, currentUser().getUserId());
    }
}
