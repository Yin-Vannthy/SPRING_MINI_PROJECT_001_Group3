package com.api.miniproject.miniproject.configuration.configure;

import com.api.miniproject.miniproject.configuration.security.JwtAuthEntrypoint;
import com.api.miniproject.miniproject.configuration.security.JwtAuthFilter;
import com.api.miniproject.miniproject.exception.CustomAccessDeniesHandler;
import com.api.miniproject.miniproject.service.serviceImpl.AppUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntrypoint jwtAuthEntrypoint;
    private final AppUserServiceImpl appUserServiceImpl;
    private final CustomAccessDeniesHandler customAccessDeniesHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, JwtAuthEntrypoint jwtAuthEntrypoint, AppUserServiceImpl appUserServiceImpl, CustomAccessDeniesHandler customAccessDeniesHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.jwtAuthEntrypoint = jwtAuthEntrypoint;
        this.appUserServiceImpl = appUserServiceImpl;
        this.customAccessDeniesHandler = customAccessDeniesHandler;
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        "/auth/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/api/v1/authentication/**"
                                ).permitAll()
                                .requestMatchers("/api/v1/author/**").hasRole("AUTHOR")
                                .requestMatchers(
                                        "/api/v1/user/**",
                                        "/api/v1/article/**",
                                        "/api/v1/files-upload/**",
                                        "/api/v1/bookmark/**",
                                        "/api/v1/comments/**"
                                ).authenticated()
                                .anyRequest().permitAll()
                )
                .userDetailsService(appUserServiceImpl)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        ex -> ex.accessDeniedHandler(customAccessDeniesHandler)
                                .authenticationEntryPoint(jwtAuthEntrypoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
