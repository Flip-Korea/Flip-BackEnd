package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.request.LoginRequest;
import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.LoginService;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtProvider jwtProvider;

  @PostMapping("/api/v1/account/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request) {

    Profile profile = loginService.login(request.provider(), request.oauthId());

    String accessToken = jwtProvider.createAccessToken(profile.getProfileId());
    String refreshToken = jwtProvider.createRefreshToken(profile.getProfileId());

    JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken);
    return ResponseEntity.ok().body(jwtResponse);
  }
}
