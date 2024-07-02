package com.flip.flipapp.domain.comment.controller;

import com.flip.flipapp.domain.comment.controller.dto.response.GetMyCommentsResponse;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentPageDto;
import com.flip.flipapp.domain.comment.service.GetMyCommentsService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class GetMyCommentsController {

  private final GetMyCommentsService getMyCommentsService;
  @GetMapping("/api/v1/my/comments")
  public ResponseEntity<Object> getMyComments(
      @RequestParam(required = false) @Min(1) Long lastCommentId,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    Page<MyCommentPageDto> page = getMyCommentsService.getMyComments(profileId,
        lastCommentId);
    return ResponseEntity.ok(GetMyCommentsResponse.from(page));
  }
}
