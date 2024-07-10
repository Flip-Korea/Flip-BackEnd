package com.flip.flipapp.domain.comment.service.dto;

public record DeleteCommentCommand(
    Long userId,
    Long postId,
    Long commentId
) {

}
