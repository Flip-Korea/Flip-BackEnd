package com.flip.flipapp.domain.follow.controller.dto.request;

import com.flip.flipapp.domain.follow.service.dto.FollowCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FollowRequest(

    @NotNull
    @Min(1)
    Long followId
) {

  public FollowCommand toCommand(Long profileId) {
    return new FollowCommand(followId, profileId);
  }
}
