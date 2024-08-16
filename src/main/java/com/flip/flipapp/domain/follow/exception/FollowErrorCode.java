package com.flip.flipapp.domain.follow.exception;

import com.flip.flipapp.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {

  DUPLICATED_FOLLOW("F001", "이미 팔로우한 유저입니다.", 400),
  SELF_FOLLOW("F002", "다른 유저만 팔로우할 수 있습니다.", 400);

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
