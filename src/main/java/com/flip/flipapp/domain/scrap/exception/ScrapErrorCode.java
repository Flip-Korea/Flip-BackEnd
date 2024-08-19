package com.flip.flipapp.domain.scrap.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScrapErrorCode implements ErrorCode {
  DUPLICATED_SCRAP("S001", "이미 스크랩한 게시물입니다.", 400),
  SELF_SCRAP("S002", "본인 게시물은 스크랩할 수 없습니다.", 400),
  SCRAP_NOT_FOUND("S003", "해당 스크랩을 찾을 수 없습니다.", 400),
  NOT_SCRAP_OWNER("S004", "스크랩한 사용자가 아닙니다.", 400);

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
