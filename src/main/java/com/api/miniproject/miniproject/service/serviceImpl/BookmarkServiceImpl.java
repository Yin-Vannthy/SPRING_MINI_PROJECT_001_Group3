package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.entity.Article;
import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.repository.ArticleRepository;
import com.api.miniproject.miniproject.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
    private final ArticleRepository articleRepository;

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





}
