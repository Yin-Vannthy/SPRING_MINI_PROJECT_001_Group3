package com.api.miniproject.miniproject.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomAccessDeniesHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String jsonResponse = String.format(
                "{\"timestamp\": \"%s\", \"status\": 403, \"error\": \"Forbidden\", \"message\": \"Your role have no permission to access this endpoint\", \"path\": \"%s\"}",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                request.getRequestURI()
        );

        response.getWriter().write(jsonResponse);
    }
}