package com.flip.flipapp.domain.profile.controller;

import com.flip.flipapp.domain.profile.controller.dto.response.GetMyProfileResponse;
import com.flip.flipapp.domain.profile.service.GetMyProfileService;
import com.flip.flipapp.global.auth.CurrentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMyProfileController {

  private final GetMyProfileService getMyProfileService;

  @GetMapping("/api/v1/my/profile")
  public ResponseEntity<GetMyProfileResponse> getMyProfile(
      @CurrentProfile Long profileId
  ) {
    GetMyProfileResponse response = getMyProfileService.getMyProfile(profileId);
    return ResponseEntity.ok(response);
  }
}
