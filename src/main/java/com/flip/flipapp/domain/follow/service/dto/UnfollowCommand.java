package com.flip.flipapp.domain.follow.service.dto;

public record UnfollowCommand(
    Long followId,
    Long profileId
) {

}
