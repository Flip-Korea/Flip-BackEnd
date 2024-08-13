package com.flip.flipapp.domain.interest_category.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class InterestCategoryRelationException extends BusinessException {

  public InterestCategoryRelationException() {
    super(InterestCategoryErrorCode.INTEREST_CATEGORY_WRONG_RELATION);
  }
}
