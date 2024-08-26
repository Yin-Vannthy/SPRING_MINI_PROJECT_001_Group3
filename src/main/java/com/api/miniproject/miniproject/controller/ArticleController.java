package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.ArticleRequest;
import com.api.miniproject.miniproject.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @Operation(summary = "Create an article")
    @PostMapping("author/article/createArticle")
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.createArticle(articleRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Get an article by Id")
    @GetMapping("article/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.getArticle(articleId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Delete an article by Id")
    @DeleteMapping("author/article/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        null,
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Update an article by Id")
    @PutMapping("author/article/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @Valid @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.updateArticle(articleId, articleRequest),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Get all articles")
    @GetMapping("article/all")
    public ResponseEntity<?> getAllArticles(
            @RequestParam(defaultValue = "0", required = false) Integer pageNo,
            @RequestParam(defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(defaultValue = "articleId", required = false) Enums.Article sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.getAllArticles(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }
}
