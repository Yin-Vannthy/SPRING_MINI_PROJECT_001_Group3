package com.api.miniproject.miniproject.configuration.util;

import com.api.miniproject.miniproject.model.response.ApiResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class APIResponseUtil {
    public static <T> ApiResponse<T> apiResponse(T payload, HttpStatus status) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .status(status)
                .code(status.value())
                .build();
    }

    public static <T> ApiResponse<T> tokenResponse(T payload, HttpStatus status, String token) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .status(status)
                .code(status.value())
                .token(token)
                .build();
    }
}
