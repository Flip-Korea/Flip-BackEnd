package com.flip.flipapp.domain.comment.repository.dto;

public record CommentsOfPostCondition(
    Long postId,
    Long cursor,
    int limit
) {

}
