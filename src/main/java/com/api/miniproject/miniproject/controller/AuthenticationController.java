package com.api.miniproject.miniproject.controller;

import com.api.miniproject.miniproject.exception.CustomNotFoundException;
import com.api.miniproject.miniproject.model.enums.Enums;
import com.api.miniproject.miniproject.model.request.AuthRequest;
import com.api.miniproject.miniproject.model.request.UserRequest;
import com.api.miniproject.miniproject.configuration.security.JwtService;
import com.api.miniproject.miniproject.service.AppUserService;
import com.api.miniproject.miniproject.configuration.util.APIResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.api.miniproject.miniproject.model.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication/")
@AllArgsConstructor
public class AuthenticationController {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails user = appUserService.loadUserByUsername(username);
            if (user == null) {
                throw new CustomNotFoundException("Wrong Email");
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new CustomNotFoundException("Wrong Password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Operation(summary = "Register as a new user")
    @PostMapping("register")
    public ResponseEntity<?> saveUser(
            @Valid @RequestBody UserRequest userRequest,
            @RequestParam(defaultValue = "AUTHOR") Enums.Roles role) {
        return ResponseEntity.ok(
                APIResponseUtil.apiResponse(
                        appUserService.saveUser(userRequest, role),
                        HttpStatus.CREATED
                )
        );
    }

    @Operation(summary = "Login via credentials to get token")
    @PostMapping("login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(authRequest.getEmail().toLowerCase());

        authenticate(authRequest.getEmail().toLowerCase(), authRequest.getPassword());
        final UserDetails userDetails = appUserService.loadUserByUsername(authRequest.getEmail().toLowerCase());
        final String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                APIResponseUtil.tokenResponse(
                        appUser.toUserResponse(),
                        HttpStatus.OK,
                        token
                )
        );
    }
}
