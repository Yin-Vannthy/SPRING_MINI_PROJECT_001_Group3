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


@RestController
@RequestMapping("/api/v1/bookmark")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping()
    @Operation(summary = "Get list of bookmark articles")
    public ResponseEntity<?> getBookmarks(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "articleId") String sortBy,
                                         @RequestParam(required = false, defaultValue = "DESC") String sortDirection)
    {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.getBookmarks(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add bookmark on any article")
    public ResponseEntity<?> bookmark(@PathVariable("id") Long articleId){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.postBookmark(articleId),
                        HttpStatus.CREATED
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change status when this article is no longer your favorite")
    public ResponseEntity<?> updateBookmark(@PathVariable("id") Long articleId){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        bookmarkService.updateBookmark(articleId),
                        HttpStatus.OK
                )
        );
    }

}
