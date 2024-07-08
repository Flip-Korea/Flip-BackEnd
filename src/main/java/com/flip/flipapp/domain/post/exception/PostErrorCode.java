package com.flip.flipapp.domain.post.exception;

import com.flip.flipapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
  POST_NOT_FOUND("P001", "해당 포스트가 존재하지않습니다.", 404);

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
