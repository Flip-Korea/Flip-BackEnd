package com.flip.flipapp.domain.scrap.controller.dto.request;

import com.flip.flipapp.domain.scrap.service.dto.ModifyScrapCommand;
import jakarta.validation.constraints.NotNull;

public record ModifyScrapRequest(
    @NotNull
    String scrapComment
) {

  public ModifyScrapCommand toCommand(Long profileId, Long scrapId) {
    return new ModifyScrapCommand(profileId, scrapId, scrapComment.trim());
  }
}
