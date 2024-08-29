package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.LoginService;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import jakarta.validation.Valid;
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

  @PostMapping("/api/v1/login")
  public ResponseEntity<JwtResponse> login(@RequestBody @Valid OauthIdRequest oauthIdRequest) {

    Profile profile = loginService.login(oauthIdRequest);

    String accessToken = jwtProvider.createAccessToken(profile.getProfileId());
    String refreshToken = jwtProvider.createRefreshToken(profile.getProfileId());
    JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken);

    return ResponseEntity.ok(jwtResponse);
  }
}
