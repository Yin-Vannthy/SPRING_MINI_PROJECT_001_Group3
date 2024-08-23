package com.api.miniproject.miniproject.service;

import com.api.miniproject.miniproject.model.dto.UserDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.UserRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AppUserService extends UserDetailsService {

    Optional<AppUser> getUserById(Long userId);

    UserDto saveUser(UserRequest userRequest, Enums.Roles role);

    UserDto getCurrentUser();

    UserDto updateCurrentUser(@Valid UserRequest userRequest, Enums.Roles role);
}
