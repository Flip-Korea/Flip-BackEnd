package com.flip.flipapp.domain.scrap.controller;

import com.flip.flipapp.domain.scrap.controller.dto.response.GetMyScrapsResponse;
import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageDto;
import com.flip.flipapp.domain.scrap.service.GetMyScrapsService;
import com.flip.flipapp.domain.scrap.service.dto.GetMyScrapsQuery;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMyScrapsController {

  private final GetMyScrapsService getMyScrapsService;

  @GetMapping("/api/v1/my/scraps")
  public ResponseEntity<Object> getMyScraps(
      @RequestParam(required = false) @Min(1) Long cursor,
      @RequestParam @Min(1) int limit,
      @CurrentProfile Long profileId
  ) {
    Page<ScrapPageDto> myScraps = getMyScrapsService.getMyScraps(
        new GetMyScrapsQuery(cursor, limit, profileId));

    return ResponseEntity.ok(GetMyScrapsResponse.from(myScraps));
  }
}
