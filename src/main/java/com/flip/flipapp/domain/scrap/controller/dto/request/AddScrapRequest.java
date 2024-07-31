package com.flip.flipapp.domain.scrap.controller.dto.request;

import com.flip.flipapp.domain.scrap.service.dto.AddScrapCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddScrapRequest(
    @NotNull
    String scrapComment,

    @NotNull
    @Min(1)
    Long postId
) {

  public AddScrapCommand toCommand(Long profileId) {
    return new AddScrapCommand(profileId, postId, scrapComment.trim());
  }
}
