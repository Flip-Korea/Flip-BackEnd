package com.flip.flipapp.domain.follow.controller;

import com.flip.flipapp.domain.follow.controller.dto.request.FollowRequest;
import com.flip.flipapp.domain.follow.model.Follow;
import com.flip.flipapp.domain.follow.service.FollowService;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class FollowController {

  private final FollowService followService;

  @PostMapping("/api/v1/follows")
  public ResponseEntity<Object> follow(
      @RequestBody @Valid FollowRequest request,
      @CurrentProfile Long profileId
  ) {
    Follow follow = followService.follow(request.toCommand(profileId));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                              .path("/{followId}")
                                              .buildAndExpand(follow.getFollowId())
                                              .toUri();

    return ResponseEntity.created(location).build();
  }
}
