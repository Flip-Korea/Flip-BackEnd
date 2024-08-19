package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.RegisterService;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService registerService;
  private final JwtProvider jwtProvider;

  @PostMapping("/api/v1/accounts")
  public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {

    Profile profile = registerService.register(registerRequest);

    String accessToken = jwtProvider.createAccessToken(profile.getProfileId());
    String refreshToken = jwtProvider.createRefreshToken(profile.getProfileId());

    JwtResponse response = new JwtResponse(accessToken, refreshToken);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
