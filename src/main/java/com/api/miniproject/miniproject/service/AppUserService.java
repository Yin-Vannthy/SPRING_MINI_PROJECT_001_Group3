package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.UserDto;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.PasswordRequest;
import com.api.miniproject.miniproject.model.request.UserRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    UserDto saveUser(UserRequest userRequest, Enums.Roles role);

    UserDto getCurrentUser();

    UserDto updateCurrentUser(@Valid UserRequest userRequest, Enums.Roles role);

    UserDto forgetPassword(@Valid PasswordRequest passwordRequest);
}
