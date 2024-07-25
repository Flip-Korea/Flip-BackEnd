package com.flip.flipapp.domain.temp_post.controller;

import com.flip.flipapp.domain.temp_post.controller.dto.response.GetTempPostsResponse;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.service.GetTempPostsService;
import com.flip.flipapp.domain.temp_post.service.dto.GetTempPostsQuery;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetTempPostsController {

  private final GetTempPostsService getTempPostsService;

  @GetMapping("/api/v1/temp-posts")
  public ResponseEntity<GetTempPostsResponse> getTempPosts(
      @RequestParam(required = false) @Min(1) Long cursor,
      @RequestParam @Min(1) int limit,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());

    Page<TempPost> tempPosts = getTempPostsService.getTempPosts(
        new GetTempPostsQuery(profileId, cursor, limit));

    return ResponseEntity.ok(GetTempPostsResponse.from(tempPosts));
  }

}
