package com.flip.flipapp.domain.follow.service.dto;

public record FollowCommand(
    Long followId,
    Long followerId
) {

}
