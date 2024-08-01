package com.flip.flipapp.domain.scrap.controller;

import com.flip.flipapp.domain.scrap.controller.dto.request.AddScrapRequest;
import com.flip.flipapp.domain.scrap.model.Scrap;
import com.flip.flipapp.domain.scrap.service.AddScrapService;
import com.flip.flipapp.global.auth.CurrentProfile;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class AddScrapController {

  private final AddScrapService addScrapService;

  @PostMapping("/api/v1/scraps")
  public ResponseEntity<Object> addScrap(
      @RequestBody @Valid AddScrapRequest request,
      @CurrentProfile Long profileId) {
    Scrap scrap = addScrapService.addScrap(request.toCommand(profileId));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                              .path("/{scrapId}")
                                              .buildAndExpand(scrap.getScrapId())
                                              .toUri();

    return ResponseEntity.created(location).build();
  }
}
