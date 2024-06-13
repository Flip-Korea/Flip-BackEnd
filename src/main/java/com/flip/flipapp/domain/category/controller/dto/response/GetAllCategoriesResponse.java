package com.flip.flipapp.domain.category.controller.dto.response;

import com.flip.flipapp.domain.category.model.Category;

public record GetAllCategoriesResponse(
    Long categoryId,
    String categoryName
) {

  public static GetAllCategoriesResponse from(Category category) {
    return new GetAllCategoriesResponse(
        category.getCategoryId(),
        category.getCategoryName().getDescription());
  }
}
