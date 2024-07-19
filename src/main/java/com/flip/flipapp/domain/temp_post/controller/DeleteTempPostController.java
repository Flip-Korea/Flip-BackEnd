package com.flip.flipapp.domain.temp_post.controller;

import com.flip.flipapp.domain.temp_post.service.DeleteTempPostService;
import com.flip.flipapp.domain.temp_post.service.dto.DeleteTempPostCommand;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class DeleteTempPostController {

  private final DeleteTempPostService deleteTempPostService;

  @DeleteMapping("/api/v1/temp-posts/{tempPostId}")
  public ResponseEntity<Object> deleteTempPost(
      @PathVariable @Min(1) Long tempPostId,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    deleteTempPostService.deleteTempPost(new DeleteTempPostCommand(profileId, tempPostId));

    return ResponseEntity.noContent().build();
  }
}
