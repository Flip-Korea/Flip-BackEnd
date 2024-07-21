package com.flip.flipapp.domain.temp_post.exception;

import com.flip.flipapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TempPostErrorCode implements ErrorCode {

  TEMP_POST_NOT_FOUND("TP001", "임시 포스트를 찾을 수 없습니다.", 400),
  NOT_TEMP_POST_WRITER("TP002", "임시 포스트 작성자가 아닙니다.", 400);

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
