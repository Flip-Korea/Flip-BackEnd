package com.flip.flipapp.domain.comment.controller.dto.request;

import com.flip.flipapp.domain.comment.service.dto.AddCommentCommand;
import jakarta.validation.constraints.NotBlank;

public record AddCommentRequest(
    @NotBlank
    String content
) {

  public AddCommentCommand toCommand(Long postId, Long profileId) {
    return new AddCommentCommand(postId, profileId, content);
  }
}
