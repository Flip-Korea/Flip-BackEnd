package com.flip.flipapp.domain.comment.repository.dto;

public record MyCommentsPageCondition(
    Long profileId,
    Long cursor,
    int limit
) {

}
