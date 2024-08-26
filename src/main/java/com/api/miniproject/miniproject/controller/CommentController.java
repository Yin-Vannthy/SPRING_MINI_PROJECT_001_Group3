package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.model.request.CommentRequest;
import com.api.miniproject.miniproject.model.response.ApiResponse;
import com.api.miniproject.miniproject.model.response.CommentResponse;
import com.api.miniproject.miniproject.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> getCommentById(@PathVariable Long id) {
        CommentResponse commentResponse = commentService.getCommentById(id);
        return ResponseEntity.ok(ApiResponse
                .<CommentResponse>builder()
                .status(HttpStatus.OK)
                .code(200)
                .payload(commentResponse)
                .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(HttpStatus.OK)
                .code(200)
                .payload(null)
                .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CommentResponse>> updateCommentById(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        CommentResponse commentResponse = commentService.updateCommentById(id, commentRequest);
        return ResponseEntity.ok(ApiResponse
                .<CommentResponse>builder()
                .status(HttpStatus.OK)
                .code(200)
                .payload(commentResponse)
                .build()
        );
    }



    /*
    -----------------------------------------------------------------

      "message": "Get comment id 5 successfully.",
      "status": "OK",
      "payload": {
        "commentId": 5,
        "comment": "I am an alien",
        "createdAt": "2024-08-25T16:46:44.771843",
        "user": {
          "userId": 25,
          "username": "Aurora",
          "email": "aurora@gmail.com",
          "address": "Mars",
          "phoneNumber": "098 899 876",
          "createdAt": "2024-08-25T16:25:06.395616",
          "role": "AUTHOR"
        }
      }
    }

    -----------------------------------------------------------------
    * */
}
