package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/comment/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Get comments by its Id")
    @GetMapping("{commentId}")
    public ResponseEntity<?> getComments(@PathVariable Long commentId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        commentService.getComment(commentId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Update comments by its Id")
    @PutMapping("{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        commentService.updateComment(commentId, commentRequest),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Delete comments by its Id")
    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                       null,
                        HttpStatus.OK
                )
        );
    }
}
