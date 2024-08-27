package com.api.miniproject.miniproject.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse <T>{
    private final String message = "Successfully";
    private HttpStatus status;
    private Integer code;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime localDateTime = LocalDateTime.now();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
}
