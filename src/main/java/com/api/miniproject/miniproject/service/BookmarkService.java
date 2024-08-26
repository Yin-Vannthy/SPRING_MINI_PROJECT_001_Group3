package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import java.util.List;

public interface BookmarkService {

    List<ArticleDto> getBookmarks(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);


    String postBookmark(Long articleId);

    String updateBookmark(Long articleId);
}
