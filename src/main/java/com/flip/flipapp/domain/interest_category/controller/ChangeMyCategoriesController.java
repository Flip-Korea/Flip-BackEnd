package com.flip.flipapp.domain.interest_category.controller;

import com.flip.flipapp.domain.interest_category.controller.dto.request.ChangeMyCategoriesRequest;
import com.flip.flipapp.domain.interest_category.service.ChangeMyCategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeMyCategoriesController {

  private final ChangeMyCategoriesService changeMyCategoriesService;

  @PutMapping("/api/v1/my/categories")
  public ResponseEntity<Object> changeMyCategories(
      @RequestBody @Valid ChangeMyCategoriesRequest request,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());

    changeMyCategoriesService.changeMyCategories(request.toCommand(profileId));

    return ResponseEntity.noContent().build();
  }
}
