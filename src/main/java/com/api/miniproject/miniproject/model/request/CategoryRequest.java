package com.api.miniproject.miniproject.model.request;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryRequest {
    @NotEmpty(message = "CategoryName Can not be empty")
    @NotBlank(message = "CategoryName Can not be blank")
    @NotNull(message = "CategoryName Can not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Category name allow only character")
    private String categoryName;

    public Category toCategoryEntity(AppUser currentUser) {
        return new Category(null, this.categoryName.trim(), null, LocalDateTime.now(), null, currentUser);
    }

}
