package com.flip.flipapp.domain.scrap.service.dto;

public record DeleteScrapCommand(
    Long profileId,
    Long scrapId
) {

}
