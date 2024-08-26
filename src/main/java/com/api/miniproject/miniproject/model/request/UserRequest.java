package com.api.miniproject.miniproject.model.request;

import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.enums.Enums;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    @NotEmpty(message = "Name Can not be empty")
    @NotBlank(message = "Name Can not be blank")
    @NotNull(message = "Name Can not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Username allow only character")
    private String username;

    @NotEmpty(message = "Address Can not be empty")
    @NotBlank(message = "Address Can not be blank")
    @NotNull(message = "Address Can not be null")
    private String address;

    @NotEmpty(message = "Address Can not be empty")
    @NotBlank(message = "Address Can not be blank")
    @NotNull(message = "Address Can not be null")
    @Pattern(regexp = "^0[1-9][0-9]{7,8}$", message = "Invalid phone number")
    private String phoneNumber;

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

    public AppUser toUserEntity(Enums.Roles role, String password) {
        return new AppUser(null, this.username,this.address, this.phoneNumber, this.email, password, role,LocalDateTime.now(), null);
    }

    public AppUser toUserEntity(Long userId, Enums.Roles role, String password) {
        return new AppUser(userId, this.username,this.address, this.phoneNumber, this.email, password, role,LocalDateTime.now(), null);
    }
}
