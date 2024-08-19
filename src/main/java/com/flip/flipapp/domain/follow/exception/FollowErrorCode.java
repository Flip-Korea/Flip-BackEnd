package com.flip.flipapp.domain.follow.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {

  DUPLICATED_FOLLOW("F001", "이미 팔로우한 유저입니다.", 400),
  SELF_FOLLOW("F002", "다른 유저만 팔로우할 수 있습니다.", 400),
  FOLLOW_NOT_FOUND("F003", "팔로우한 유저가 존재하지 않습니다.", 400),
  NOT_FOLLOWER("F004", "팔로워가 아닙니다.", 400);

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
