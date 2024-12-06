package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutController {

  private final TokenService tokenService;

  @DeleteMapping("/api/v1/logout")
  public ResponseEntity<Void> logout(@RequestHeader("Authorization") String RefreshToken) {
    tokenService.logout(RefreshToken);
    return ResponseEntity.noContent().build();
  }
}
