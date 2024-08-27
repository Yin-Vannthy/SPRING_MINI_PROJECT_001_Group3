package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.dto.ArticleDto;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.ArticleRequest;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.response.ApiResponse;
import com.api.miniproject.miniproject.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @Operation(summary = "Create an article")
    @PostMapping("author/article/createArticle")
    public ResponseEntity<ApiResponse<ArticleDto>> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.createArticle(articleRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Get an article by Id")
    @GetMapping("article/{articleId}")
    public ResponseEntity<ApiResponse<ArticleDto>> getArticle(@PathVariable @Min(1) Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.getArticle(articleId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Delete an article by Id")
    @DeleteMapping("author/article/{articleId}")
    public ResponseEntity<ApiResponse<?>> deleteArticle(@PathVariable @Min(1) Long articleId) {
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
    public ResponseEntity<ApiResponse<ArticleDto>> updateArticle(@PathVariable @Min(1) Long articleId, @Valid @RequestBody ArticleRequest articleRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.updateArticle(articleId, articleRequest),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Get all articles")
    @GetMapping("article/all")
    public ResponseEntity<ApiResponse<List<ArticleDto>>> getAllArticles(
            @RequestParam(defaultValue = "0", required = false) @Min(0) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) @Min(1) Integer pageSize,
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

    @Operation(summary = "Create a comment on an article via its Id")
    @PostMapping("article/{articleId}/comment")
    public ResponseEntity<ApiResponse<ArticleDto>> createComment(@PathVariable @Min(1) Long articleId, @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.createComment(articleId, commentRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Get comments on an article via its Id")
    @GetMapping("article/{articleId}/comment")
    public ResponseEntity<ApiResponse<ArticleDto>> getComments(@PathVariable @Min(1) Long articleId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        articleService.getComments(articleId),
                        HttpStatus.OK
                )
        );
    }
}
