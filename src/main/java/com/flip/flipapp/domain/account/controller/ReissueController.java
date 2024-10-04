package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.CheckAccountStatusService;
import com.flip.flipapp.domain.token.service.TokenService;
import com.flip.flipapp.global.auth.CurrentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

  private final CheckAccountStatusService checkAccountStatusService;
  private final TokenService tokenService;

  @PostMapping("/api/v1/reissue")
  public ResponseEntity<JwtResponse> reissue(
      @RequestHeader("Authorization") String currentRefreshToken,
      @CurrentProfile Long profileId) {

    checkAccountStatusService.checkAccountStatus(profileId);
    JwtResponse jwtResponse = tokenService.reissue(currentRefreshToken, profileId);

    return ResponseEntity.ok(jwtResponse);
  }

}
