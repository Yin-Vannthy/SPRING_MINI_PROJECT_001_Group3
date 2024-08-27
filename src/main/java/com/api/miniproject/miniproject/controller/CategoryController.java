package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.model.dto.CategoryDto;
import com.api.miniproject.miniproject.model.response.ApiResponse;
import com.api.miniproject.miniproject.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.CategoryRequest;
import com.api.miniproject.miniproject.service.CategoryService;
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
@RequestMapping("/api/v1/author/category/")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create a category")
    @PostMapping("createCategory")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        categoryService.createCategory(categoryRequest),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Get a category by Id")
    @GetMapping("getCategory/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategory(@PathVariable @Min(1) Long categoryId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        categoryService.getCategory(categoryId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Update a category by Id")
    @PutMapping("updateCategory/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@RequestBody @Min(1) CategoryRequest categoryRequest, @PathVariable Long categoryId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        categoryService.updateCategory(categoryRequest, categoryId),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Get all categories")
    @GetMapping("getCategories/all")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategories(
            @RequestParam(defaultValue = "0", required = false) @Min(0) Integer pageNo,
            @RequestParam(defaultValue = "10", required = false) @Min(1) Integer pageSize,
            @RequestParam(defaultValue = "categoryId", required = false) Enums.Category sortBy,
            @RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection
    ) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        categoryService.getCategories(pageNo, pageSize, sortBy, sortDirection),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Delete a category by Id")
    @DeleteMapping("deleteCategory/{categoryId}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable @Min(1) Long categoryId) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        categoryService.deleteCategory(categoryId),
                        HttpStatus.OK
                )
        );
    }
}
