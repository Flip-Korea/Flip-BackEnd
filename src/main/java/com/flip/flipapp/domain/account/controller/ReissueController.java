package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.ReissueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

  private final ReissueService reissueService;

  @PostMapping("/api/v1/reissue")
  public ResponseEntity<JwtResponse> reissue(
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());

    JwtResponse jwtResponse = reissueService.reissue(profileId);

    return ResponseEntity.ok(jwtResponse);
  }

}
