package com.flip.flipapp.domain.category.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class CategoryNotFoundException extends BusinessException {

  public CategoryNotFoundException() {
    super(CategoryErrorCode.CATEGORY_NOT_FOUND);
  }
}
