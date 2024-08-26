package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/bookmark")
@AllArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Operation(summary = "Create a bookmark")
    @PostMapping("create/{articleId}")
    public ResponseEntity<?> createBookmark(@PathVariable Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.createBookmark(articleId),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Update a bookmark")
    @PutMapping("update/{articleId}")
    public ResponseEntity<?> updateBookmark(@PathVariable Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.updateBookmark(articleId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Get all bookmarks")
    @GetMapping
    public ResponseEntity<?> getBookmarks(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
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
