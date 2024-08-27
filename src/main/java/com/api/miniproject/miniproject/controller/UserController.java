package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import com.api.miniproject.miniproject.model.dto.UserDto;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.UserRequest;
import com.api.miniproject.miniproject.model.response.ApiResponse;
import com.api.miniproject.miniproject.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/user/")
@AllArgsConstructor
public class UserController {
    private final AppUserService appUserService;

    @Operation(summary = "Get current user")
    @GetMapping("getCurrentUser")
    public ResponseEntity<ApiResponse<?>> getCurrentUser() {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        appUserService.getCurrentUser(),
                        HttpStatus.OK
                )
        );
    }

    @Operation(summary = "Update current user's information")
    @PutMapping("updateCurrentUser")
    public ResponseEntity<ApiResponse<UserDto>> updateCurrentUser(
            @Valid @RequestBody UserRequest userRequest,
            @RequestParam(defaultValue = "AUTHOR") Enums.Roles role
    ) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        appUserService.updateCurrentUser(userRequest, role),
                        HttpStatus.OK
                )
        );
    }
}
