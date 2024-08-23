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

import java.util.Optional;


@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomNotFoundException("No user found with email " + email)
        );
    }

    @Override
    public UserDto saveUser(UserRequest userRequest, Enums.Roles role) {
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new CustomNotFoundException("Confirm Passwords don't match");
        }

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new CustomNotFoundException("This email is already in use");
        }
        return userRepository.save(userRequest.toUserEntity(role, passwordEncoder.encode(userRequest.getPassword()))).toUserResponse();
    }

    @Override
    public UserDto getCurrentUser() {
        return CurrentUser.getCurrentUser().toUserResponse();
    }

    @Override
    public UserDto updateCurrentUser(UserRequest userRequest, Enums.Roles role) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent(user -> {
            if (!user.getUserId().equals(CurrentUser.getCurrentUser().getUserId())) {
                throw new CustomNotFoundException("This email is already in use by another user.");
            }
        });

        return userRepository.save(userRequest.toUserEntity(CurrentUser.getCurrentUser().getUserId(), role, passwordEncoder.encode(userRequest.getPassword()))).toUserResponse();
    }

    @Override
    public Optional<AppUser> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
