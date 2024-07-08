package com.flip.flipapp.domain.comment.controller;

import com.flip.flipapp.domain.comment.controller.dto.response.GetCommentsOfPostResponse;
import com.flip.flipapp.domain.comment.repository.dto.CommentOfPostDto;
import com.flip.flipapp.domain.comment.service.GetCommentsOfPostService;
import com.flip.flipapp.domain.comment.service.dto.GetCommentsOfPostQuery;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class GetCommentsOfPostController {

  private final GetCommentsOfPostService getCommentsOfPostService;

  @GetMapping("/api/v1/posts/{postId}/comments")
  public ResponseEntity<GetCommentsOfPostResponse> getCommentsOfPost(
      @PathVariable @Min(1) Long postId,
      @RequestParam(required = false) @Min(1) Long cursor,
      @RequestParam @Min(1) int limit) {

    Page<CommentOfPostDto> page = getCommentsOfPostService.getCommentsOfPost(
        new GetCommentsOfPostQuery(postId, cursor, limit));

    return ResponseEntity.ok(GetCommentsOfPostResponse.from(page));
  }
}
