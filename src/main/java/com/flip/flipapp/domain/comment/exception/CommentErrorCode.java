package com.flip.flipapp.domain.comment.exception;

import com.flip.flipapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

  COMMENT_NOT_FOUND("CM001", "댓글을 찾을 수 없습니다.", 400),
  NOT_COMMENT_WRITER("CM002", "댓글 작성자가 아닙니다.", 400),
  INVALID_POST_OF_COMMENT("CM003", "잘못된 포스트입니다", 400);

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
