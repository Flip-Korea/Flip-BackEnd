package com.flip.flipapp.domain.temp_post.controller;

import com.flip.flipapp.domain.temp_post.controller.dto.request.ModifyTempPostRequest;
import com.flip.flipapp.domain.temp_post.service.ModifyTempPostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class ModifyTempPostController {

  private final ModifyTempPostService modifyTempPostService;

  @PutMapping("/api/v1/temp-posts/{tempPostId}")
  public ResponseEntity<Object> modifyTempPost(
      @PathVariable @Min(1) Long tempPostId,
      @RequestBody @Valid ModifyTempPostRequest request,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    modifyTempPostService.modifyTempPost(request.toCommand(profileId, tempPostId));

    return ResponseEntity.noContent().build();
  }
}
