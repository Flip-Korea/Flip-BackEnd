package com.flip.flipapp.domain.post_tag.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostTagErrorCode implements ErrorCode {

  OVER_MAX_COUNT("PT001", "태그는 최대 10개까지만 등록할 수 있습니다.", 400);

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
