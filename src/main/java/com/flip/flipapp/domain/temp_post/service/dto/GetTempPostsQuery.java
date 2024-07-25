package com.flip.flipapp.domain.temp_post.service.dto;

import com.flip.flipapp.domain.temp_post.repository.dto.TempPostPageCondition;

public record GetTempPostsQuery(
    Long profileId,
    Long cursor,
    int limit
) {

  public TempPostPageCondition toCondition() {
    return new TempPostPageCondition(profileId, cursor, limit);
  }
}
