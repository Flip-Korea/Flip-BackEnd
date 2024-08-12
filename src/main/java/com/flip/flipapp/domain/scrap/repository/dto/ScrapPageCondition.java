package com.flip.flipapp.domain.scrap.repository.dto;

public record ScrapPageCondition(
    Long profileId,
    Long cursor,
    int limit
) {

}
