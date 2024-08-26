package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.model.entity.Bookmark;
import com.api.miniproject.miniproject.model.response.BookmarkResponse;
import com.api.miniproject.miniproject.service.BookmarkService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping()
    public List<BookmarkResponse> getBookmarks(){
        return bookmarkService.getBookmarks();
    }

    @PostMapping("/{id}")
    public Bookmark bookmark(@PathVariable("id") Long id){
        return bookmarkService.bookmark(id);
    }
}
