package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.GetRecentProfileService;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.token.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final GetRecentProfileService getRecentProfileService;
  private final TokenService tokenService;

  @PostMapping("/api/v1/login")
  public ResponseEntity<JwtResponse> login(@RequestBody @Valid OauthIdRequest oauthIdRequest) {

    Profile recentProfile = getRecentProfileService.getRecentProfile(oauthIdRequest);
    JwtResponse jwtResponse = tokenService.createAndSaveTokens(recentProfile);

    return ResponseEntity.ok(jwtResponse);
  }
}
