package com.flip.flipapp.domain.post.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
  POST_NOT_FOUND("P001", "해당 포스트가 존재하지않습니다.", 400),
  NOT_POST_WRITER("P002", "작성자가 아닙니다.", 400);

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
