package com.flip.flipapp.domain.post.controller;

import com.flip.flipapp.domain.post.controller.dto.request.ModifyPostRequest;
import com.flip.flipapp.domain.post.service.ModifyPostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ModifyPostController {

  private final ModifyPostService modifyPostService;

  @PutMapping("/api/v1/posts/{postId}")
  public ResponseEntity<Object> modifyPost(
      @PathVariable @Min(1) Long postId,
      @RequestBody @Valid ModifyPostRequest request,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    modifyPostService.modifyPost(request.toCommand(postId, profileId));

    return ResponseEntity.noContent().build();
  }
}
