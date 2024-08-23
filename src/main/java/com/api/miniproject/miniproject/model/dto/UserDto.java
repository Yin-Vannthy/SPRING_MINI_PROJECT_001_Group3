package com.api.miniproject.miniproject.model.dto;

import com.api.miniproject.miniproject.model.enums.Enums;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long userId;
    private String username;
    private String address;
    private String phoneNumber;
    private String email;
    private Enums.Roles role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDto(Long userId, String username, String phoneNumber, String address, String email, Enums.Roles role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
