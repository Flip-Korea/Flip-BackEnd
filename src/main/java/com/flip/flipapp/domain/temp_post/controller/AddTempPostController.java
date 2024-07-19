package com.flip.flipapp.domain.temp_post.controller;

import com.flip.flipapp.domain.temp_post.controller.dto.request.AddTempPostRequest;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.service.AddTempPostService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class AddTempPostController {

  private final AddTempPostService addTempPostService;

  @PostMapping("/api/v1/temp-posts")
  public ResponseEntity<Object> addTempPost(
      @RequestBody @Valid AddTempPostRequest addTempPostRequest,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Long profileId = Long.valueOf(userDetails.getUsername());

    TempPost tempPost = addTempPostService.addTempPost(addTempPostRequest.toCommand(profileId));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                              .path("/{postId}")
                                              .buildAndExpand(tempPost.getPostId())
                                              .toUri();

    return ResponseEntity.created(location).build();
  }
}
