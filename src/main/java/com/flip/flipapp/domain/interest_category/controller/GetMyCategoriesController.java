package com.flip.flipapp.domain.interest_category.controller;

import com.flip.flipapp.domain.interest_category.controller.dto.response.GetMyCategoriesResponse;
import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import com.flip.flipapp.domain.interest_category.service.GetMyCategoriesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMyCategoriesController {

  private final GetMyCategoriesService getMyCategoriesService;

  @GetMapping("/api/v1/my/categories")
  public ResponseEntity<List<GetMyCategoriesResponse>> getMyCategories(
      @AuthenticationPrincipal UserDetails userDetails) {
    Long profileId = Long.valueOf(userDetails.getUsername());

    List<InterestCategory> myCategories = getMyCategoriesService.getMyCategories(profileId);
    List<GetMyCategoriesResponse> response = myCategories.stream()
                                                         .map(ic -> GetMyCategoriesResponse.from(
                                                             ic.getCategory()))
                                                         .toList();
    return ResponseEntity.ok(response);
  }
}
