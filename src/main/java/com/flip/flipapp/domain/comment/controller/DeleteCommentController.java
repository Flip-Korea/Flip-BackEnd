package com.flip.flipapp.domain.comment.controller;

import com.flip.flipapp.domain.comment.service.DeleteCommentService;
import com.flip.flipapp.domain.comment.service.dto.DeleteCommentCommand;
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
public class DeleteCommentController {

  private final DeleteCommentService deleteCommentService;

  @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}")
  public ResponseEntity<Object> deleteComment(
      @PathVariable @Min(1) Long postId,
      @PathVariable @Min(1) Long commentId,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Long userId = Long.valueOf(userDetails.getUsername());
    deleteCommentService.deleteComment(
        new DeleteCommentCommand(userId, postId, commentId)
    );

    return ResponseEntity.noContent().build();
  }
}
