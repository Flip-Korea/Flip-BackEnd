package com.flip.flipapp.domain.post.controller;

import com.flip.flipapp.domain.post.service.DeletePostService;
import com.flip.flipapp.domain.post.service.dto.DeletePostCommand;
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
public class DeletePostController {

  private final DeletePostService deletePostService;

  @DeleteMapping("/api/v1/posts/{postId}")
  public ResponseEntity<Object> deletePost(
      @PathVariable @Min(1) Long postId,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    deletePostService.deletePost(new DeletePostCommand(profileId, postId));

    return ResponseEntity.noContent().build();
  }
}
