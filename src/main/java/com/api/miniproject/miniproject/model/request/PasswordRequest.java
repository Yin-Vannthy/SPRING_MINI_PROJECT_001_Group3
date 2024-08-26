package com.api.miniproject.miniproject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasswordRequest {
    @NotEmpty(message = "Email Can not be empty")
    @NotBlank(message = "Email Can not be blank")
    @NotNull(message = "Email Can not be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Password Can not be empty")
    @NotBlank(message = "Password Can not be blank")
    @NotNull(message = "Password Can not be null")
    @Pattern(regexp = "^.{8,}$", message = "Password must be at least 8 characters long.")
    private String password;

    private String confirmPassword;
}
