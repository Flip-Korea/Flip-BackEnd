package com.flip.flipapp.domain.interest_category.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InterestCategoryErrorCode implements ErrorCode {
  INTEREST_CATEGORY_WRONG_RELATION("IC001", "잘못된 카테고리입니다.", 400);

  private final String code;
  private final String message;
  private final int status;

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public int getStatus() {
    return this.status;
  }
}
