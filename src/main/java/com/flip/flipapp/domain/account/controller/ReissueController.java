package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.ReissueService;
import com.flip.flipapp.global.auth.CurrentProfile;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

  private final ReissueService reissueService;
  private final JwtProvider jwtProvider;

  @PostMapping("/api/v1/reissue")
  public ResponseEntity<JwtResponse> reissue(
      @RequestHeader("Authorization") String token,
      @CurrentProfile Long profileId) {

    jwtProvider.validateRefreshToken(token);

    Long validProfileId = reissueService.reissue(profileId);

    String accessToken = jwtProvider.createAccessToken(validProfileId);
    String refreshToken = jwtProvider.createRefreshToken(validProfileId);

    JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken);

    return ResponseEntity.ok(jwtResponse);
  }

}
