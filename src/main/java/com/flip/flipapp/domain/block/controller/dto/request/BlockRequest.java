package com.flip.flipapp.domain.block.controller.dto.request;

import com.flip.flipapp.domain.block.service.dto.BlockCommand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BlockRequest(
    @NotNull
    @Min(1)
    Long blockedProfileId
) {

  public BlockCommand toCommand(Long blockerProfileId) {
    return new BlockCommand(blockerProfileId, blockedProfileId);
  }

}
