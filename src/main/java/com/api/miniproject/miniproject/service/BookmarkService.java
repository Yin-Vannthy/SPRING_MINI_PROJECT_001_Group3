package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.response.BookmarkResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookmarkService {
    Bookmark bookmark(Long articleId);

    List<BookmarkResponse> getBookmarks();
}
