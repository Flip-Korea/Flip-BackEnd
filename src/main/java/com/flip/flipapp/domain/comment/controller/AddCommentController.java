package com.flip.flipapp.domain.comment.controller;

import com.flip.flipapp.domain.comment.controller.dto.request.AddCommentRequest;
import com.flip.flipapp.domain.comment.service.AddCommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AddCommentController {

  private final AddCommentService addCommentService;

  @PostMapping("/api/v1/posts/{postId}/comments")
  public ResponseEntity<Object> addComment(@PathVariable @Min(1) Long postId,
      @RequestBody @Valid AddCommentRequest addCommentRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    addCommentService.addComment(addCommentRequest.toCommand(postId, profileId));

    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
