package com.flip.flipapp.domain.comment.service.dto;

import com.flip.flipapp.domain.comment.repository.dto.MyCommentsPageCondition;

public record GetMyCommentsQuery(
    Long profileId,
    Long cursor,
    int limit
) {

  public MyCommentsPageCondition toCondition() {
    return new MyCommentsPageCondition(profileId, cursor, limit);
  }
}
