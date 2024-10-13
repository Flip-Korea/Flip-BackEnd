package com.flip.flipapp.domain.block.service.dto;

public record BlockCommand(
    Long blockerProfileId,
    Long blockedProfileId
) {

}
