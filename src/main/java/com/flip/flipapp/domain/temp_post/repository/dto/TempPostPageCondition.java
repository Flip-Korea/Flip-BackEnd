package com.flip.flipapp.domain.temp_post.repository.dto;

public record TempPostPageCondition(
    Long profileId,
    Long cursor,
    int limit
) {

}
