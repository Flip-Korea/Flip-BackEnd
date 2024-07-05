package com.flip.flipapp.domain.comment.service.dto;

import com.flip.flipapp.domain.comment.repository.dto.CommentsOfPostCondition;

public record GetCommentsOfPostQuery(
    Long postId,
    Long cursor,
    int limit
) {

  public CommentsOfPostCondition toCondition() {
    return new CommentsOfPostCondition(postId, cursor, limit);
  }
}
