package com.flip.flipapp.domain.comment.service;

public record AddCommentCommand(
    Long postId,
    Long profileId,
    String content
) {

}
