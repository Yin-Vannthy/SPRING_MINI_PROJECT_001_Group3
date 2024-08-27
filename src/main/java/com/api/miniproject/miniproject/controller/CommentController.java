package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.response.ApiResponse;
import com.api.miniproject.miniproject.model.dto.CommentDto;
import com.api.miniproject.miniproject.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CommentDto>> getCommentById(@PathVariable @Min(1) Long id) {

        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        commentService.getCommentById(id),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> deleteCommentById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        commentService.deleteCommentById(id),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CommentDto>> updateCommentById(@PathVariable @Min(1) Long id, @RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        commentService.updateCommentById(id, commentRequest),
                        HttpStatus.OK
                )
        );
    }
}
