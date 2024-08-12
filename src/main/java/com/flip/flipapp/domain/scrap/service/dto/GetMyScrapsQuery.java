package com.flip.flipapp.domain.scrap.service.dto;

import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageCondition;

public record GetMyScrapsQuery(
    Long cursor,
    int limit,
    Long profileId
) {

  public ScrapPageCondition toCondition() {
    return new ScrapPageCondition(profileId, cursor, limit);
  }
}
