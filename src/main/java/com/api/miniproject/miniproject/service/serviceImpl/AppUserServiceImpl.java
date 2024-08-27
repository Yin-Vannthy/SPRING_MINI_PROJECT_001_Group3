package com.api.miniproject.miniproject.service.serviceImpl;

import com.api.miniproject.miniproject.configuration.configure.CurrentUser;
import com.api.miniproject.miniproject.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.dto.UserDto;
import com.api.miniproject.miniproject.model.entity.AppUser;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.UserRequest;
import com.api.miniproject.miniproject.repository.UserRepository;
import com.api.miniproject.miniproject.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email.toLowerCase().trim()).orElseThrow(
                () -> new CustomNotFoundException("No user found with email " + email)
        );
    }

    @Override
    public UserDto saveUser(UserRequest userRequest, Enums.Roles role) {
        if (!userRequest.getPassword().trim().equals(userRequest.getConfirmPassword().trim())) {
            throw new CustomNotFoundException("Confirm Passwords don't match");
        }

        if (userRepository.findByEmail(userRequest.getEmail().toLowerCase().trim()).isPresent()) {
            throw new CustomNotFoundException("This email is already in use");
        }
        return userRepository.save(userRequest.toUserEntity(role.name(), passwordEncoder.encode(userRequest.getPassword()).trim())).toUserResponse();
    }

    @Override
    public UserDto getCurrentUser() {
        return CurrentUser.getCurrentUser().toUserResponse();
    }

    @Override
    public UserDto updateCurrentUser(UserRequest userRequest, Enums.Roles role) {
        AppUser user = userRepository.findByEmail(userRequest.getEmail().toLowerCase().trim())
                .filter(u -> u.getUserId().equals(CurrentUser.getCurrentUser().getUserId()))
                .orElseThrow(() -> new CustomNotFoundException("This email is already in use by another user."));

        if (!userRequest.getPassword().trim().equals(userRequest.getConfirmPassword().trim())) {
            throw new CustomNotFoundException("Confirm Passwords don't match");
        }

        return userRepository
                .save(userRequest.toUserEntity(user.getCreatedAt() ,CurrentUser.getCurrentUser().getUserId(), role.name(), passwordEncoder.encode(userRequest.getPassword().trim())))
                .toUserResponse();
    }

}

