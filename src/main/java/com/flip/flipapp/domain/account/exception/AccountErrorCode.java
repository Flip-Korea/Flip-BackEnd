package com.flip.flipapp.domain.account.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

  ACCOUNT_SUSPENDED("A001", "계정이 정지되었습니다.", 403),
  ACCOUNT_NOT_FOUND("A002", "계정을 찾을 수 없습니다.(회원가입 진행)", 404),
  DUPLICATE_OAUTH_ID("A003", "이미 존재하는 회원입니다.", 409);
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
