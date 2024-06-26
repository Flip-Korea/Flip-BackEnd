package com.flip.flipapp.domain.interest_category.controller.dto.response;

import com.flip.flipapp.domain.category.model.Category;

public record GetMyCategoriesResponse(
    Long categoryId,
    String categoryName
) {

  public static GetMyCategoriesResponse from(Category category) {
    return new GetMyCategoriesResponse(
        category.getCategoryId(),
        category.getCategoryName().getDescription());
  }
}
