package com.api.miniproject.miniproject.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {
    @NotEmpty(message = "Email Can not be empty")
    @NotBlank(message = "Email Can not be blank")
    @NotNull(message = "Email Can not be null")
    private String email;

    @NotEmpty(message = "Password Can not be empty")
    @NotBlank(message = "Password Can not be blank")
    @NotNull(message = "Password Can not be null")
    private String password;
}
