package com.flip.flipapp.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flip.flipapp.global.error.exception.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String AUTHORIZATION_TYPE = "Bearer ";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwtToken = getJwtFromRequest(request);
    try {
      if (StringUtils.hasText(jwtToken)) {
        jwtProvider.validateToken(jwtToken);
        String username = jwtProvider.getSubjectFromToken(jwtToken);
        UserDetails userDetails = getUserDetails(username);
        Authentication authentication = getAuthentication(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (BusinessException e) {
      log.info("Exception: {}, Message: {}", e.getClass(), e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_TYPE)) {
      return bearerToken.substring(AUTHORIZATION_TYPE.length());
    }
    return null;
  }

  private Authentication getAuthentication(UserDetails userDetails) {
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private UserDetails getUserDetails(String profileId) {

    return User.builder()
        .username(profileId)
        .password("")
        .authorities("ROLE_USER")
        .build();
  }

}

