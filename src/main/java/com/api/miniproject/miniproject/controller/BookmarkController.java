package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.response.ApiResponse;
import com.api.miniproject.miniproject.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/bookmark")
@AllArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Operation(summary = "Create a bookmark")
    @PostMapping("create/{articleId}")
    public ResponseEntity<ApiResponse<String>> createBookmark(@PathVariable @Min(1) Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.createBookmark(articleId),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Update a bookmark")
    @PutMapping("update/{articleId}")
    public ResponseEntity<ApiResponse<String>> updateBookmark(@PathVariable @Min(1) Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.updateBookmark(articleId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Get all bookmarks")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleDto>>> getBookmarks(
            @RequestParam(defaultValue = "0", required = false) @Min(0) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "articleId", required = false) Enums.Article sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.getBookmarks(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }
}
