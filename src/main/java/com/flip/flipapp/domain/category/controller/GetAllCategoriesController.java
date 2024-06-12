package com.flip.flipapp.domain.category.controller;

import com.flip.flipapp.domain.category.controller.dto.response.GetAllCategoriesResponse;
import com.flip.flipapp.domain.category.service.GetAllCategoriesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllCategoriesController {

  private final GetAllCategoriesService getAllCategoriesService;

  @GetMapping("/api/v1/categories")
  public ResponseEntity<List<GetAllCategoriesResponse>> getAllCategories() {
    List<GetAllCategoriesResponse> responses =
        getAllCategoriesService.getAllCategories().stream()
                               .map(GetAllCategoriesResponse::from)
                               .toList();

    return ResponseEntity.ok(responses);
  }

}
