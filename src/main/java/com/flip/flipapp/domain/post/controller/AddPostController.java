package com.flip.flipapp.domain.post.controller;

import com.flip.flipapp.domain.post.controller.dto.request.AddPostRequest;
import com.flip.flipapp.domain.post.model.Post;
import com.flip.flipapp.domain.post.service.AddPostService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Validated
public class AddPostController {

  private final AddPostService addPostService;

  @PostMapping("/api/v1/posts")
  public ResponseEntity<Object> addPost(
      @RequestBody @Valid AddPostRequest addPostRequest,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    Long profileId = Long.valueOf(userDetails.getUsername());
    Post savedPost = addPostService.addPost(addPostRequest.toCommand(profileId));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                              .path("/{postId}")
                                              .buildAndExpand(savedPost.getPostId())
                                              .toUri();

    return ResponseEntity.created(location).build();
  }
}
