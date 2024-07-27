package com.flip.flipapp.domain.post.service.dto;

public record DeletePostCommand(
    Long profileId,
    Long postId
) {

}
