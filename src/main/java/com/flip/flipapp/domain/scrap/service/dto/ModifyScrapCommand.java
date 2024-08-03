package com.flip.flipapp.domain.scrap.service.dto;

import com.flip.flipapp.domain.scrap.model.Scrap;

public record ModifyScrapCommand(
    Long profileId,
    Long scrapId,
    String scrapComment
) {

  public Scrap toScrap() {
    return Scrap.builder().scrapComment(scrapComment).build();
  }
}
