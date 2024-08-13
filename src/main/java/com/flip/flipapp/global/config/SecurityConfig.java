package com.flip.flipapp.global.config;

import com.flip.flipapp.global.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final AccessDeniedHandler customAccessDeniedHandler;
  private final AuthenticationEntryPoint customAuthenticationEntryPoint;
  private final JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .accessDeniedHandler(customAccessDeniedHandler)
            .authenticationEntryPoint(customAuthenticationEntryPoint))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/error/**", "/actuator/**", "/docs/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/validations/nickname").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/validations/user-id").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/account/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v1/account/register").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
