package com.flip.flipapp.domain.follow.controller;

import com.flip.flipapp.domain.follow.service.UnfollowService;
import com.flip.flipapp.domain.follow.service.dto.UnfollowCommand;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UnfollowController {

  private final UnfollowService unfollowService;

  @DeleteMapping("/api/v1/follows/{followId}")
  public ResponseEntity<Object> unfollow(
      @PathVariable @Min(1) Long followId,
      @CurrentProfile Long profileId
  ) {
    unfollowService.unfollow(new UnfollowCommand(followId, profileId));
    return ResponseEntity.noContent().build();
  }
}
