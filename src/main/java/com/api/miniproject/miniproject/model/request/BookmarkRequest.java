package com.api.miniproject.miniproject.model.request;

import com.api.miniproject.miniproject.model.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {
    private Long productId;
}
