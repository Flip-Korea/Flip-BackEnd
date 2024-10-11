package com.flip.flipapp.domain.block.exception;

import com.flip.flipapp.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BlockErrorCode implements ErrorCode {

  DUPLICATED_BLOCK("B001", "이미 차단한 유저입니다.", 400),
  SELF_BLOCK("B002", "자기 자신을 차단할 수 없습니다.", 400),
  BLOCK_NOT_FOUND("B003", "차단한 유저가 존재하지 않습니다.", 400),
  NOT_BLOCKER("B004", "차단자가 아닙니다.", 400);

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
