package com.flip.flipapp.domain.block.service.dto;

public record UnblockCommand(
    Long blockId,
    Long blockerProfileId
) {

}
