package com.flip.flipapp.domain.comment.service.dto;

public record AddCommentCommand(
    Long postId,
    Long profileId,
    String content
) {

}
