package com.flip.flipapp.domain.temp_post.service.dto;

public record DeleteTempPostCommand(
    Long profileId,
    Long tempPostId
) {

}
