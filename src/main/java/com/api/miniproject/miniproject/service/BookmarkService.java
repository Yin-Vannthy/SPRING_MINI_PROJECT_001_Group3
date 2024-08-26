package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.enums.Enums;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BookmarkService {
    String createBookmark(Long articleId);

    String updateBookmark(Long articleId);

    List<ArticleDto> getBookmarks(Integer pageNo, Integer pageSize, Enums.Article sortBy, Sort.Direction sortDirection);
}
