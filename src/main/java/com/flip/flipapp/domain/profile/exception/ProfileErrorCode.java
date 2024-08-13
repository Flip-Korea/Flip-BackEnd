package com.flip.flipapp.domain.profile.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProfileErrorCode implements ErrorCode {

  PROFILE_DUPLICATE_USER_ID("P001", "중복된 사용자 아이디입니다.", 409);

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