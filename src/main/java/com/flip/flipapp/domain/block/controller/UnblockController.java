package com.flip.flipapp.domain.block.controller;

import com.flip.flipapp.domain.block.service.UnblockService;
import com.flip.flipapp.domain.block.service.dto.UnblockCommand;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UnblockController {

  private final UnblockService unblockService;

  @DeleteMapping("api/v1/block/{blockId}")
  public ResponseEntity<Object> unblock(
      @PathVariable @Min(1) Long blockId,
      @CurrentProfile Long blockerProfileId
  ) {
    unblockService.unblock(new UnblockCommand(blockId, blockerProfileId));
    return ResponseEntity.noContent().build();
  }
}
