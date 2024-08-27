package com.api.miniproject.miniproject.util;

import com.api.miniproject.miniproject.model.response.ApiResponse;
import org.springframework.http.HttpStatus;

public class APIResponseUtil {
    public static <T> ApiResponse<T> apiResponse(T payload, HttpStatus status) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .status(status)
                .code(status.value())
                .build();
    }
}
