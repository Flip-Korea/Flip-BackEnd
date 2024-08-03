package com.flip.flipapp.domain.scrap.controller;

import com.flip.flipapp.domain.scrap.controller.dto.request.ModifyScrapRequest;
import com.flip.flipapp.domain.scrap.service.ModifyScrapService;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class ModifyScrapController {

  private final ModifyScrapService modifyScrapService;

  @PutMapping("/api/v1/scraps/{scrapId}")
  public ResponseEntity<Object> modifyScrap(
      @CurrentProfile Long profileId,
      @PathVariable @Min(1) Long scrapId,
      @RequestBody @Valid ModifyScrapRequest request) {

    modifyScrapService.modifyScrap(request.toCommand(profileId, scrapId));
    return ResponseEntity.noContent().build();
  }
}
