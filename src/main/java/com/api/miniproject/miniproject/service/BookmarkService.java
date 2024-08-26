package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.response.BookmarkResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookmarkService {

    List<ArticleDto> getBookmarks(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);


}
