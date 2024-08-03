package com.flip.flipapp.domain.scrap.controller;

import com.flip.flipapp.domain.scrap.service.DeleteScrapService;
import com.flip.flipapp.domain.scrap.service.dto.DeleteScrapCommand;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class DeleteScrapController {

  private final DeleteScrapService deleteScrapService;

  @DeleteMapping("/api/v1/scraps/{scrapId}")
  public ResponseEntity<Object> deleteScrap(
      @CurrentProfile Long profileId,
      @PathVariable @Min(1) Long scrapId) {
    deleteScrapService.deleteScrap(new DeleteScrapCommand(profileId, scrapId));

    return ResponseEntity.noContent().build();
  }
}
