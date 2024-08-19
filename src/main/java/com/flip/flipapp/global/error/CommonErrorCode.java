package com.flip.flipapp.global.error;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

  INVALID_INPUT_VALUE("C001", "올바르지 않은 입력 값입니다.", 400),
  METHOD_NOT_ALLOWED("C002", " 올바르지 않은 호출입니다.", 405),
  INTERNAL_SERVER_ERROR("C003", "서버 에러", 500),
  INVALID_TYPE_VALUE("C004", "바르지 않은 타입의 값을 입력했습니다.", 400),
  INVALID_REQUEST("C005", "잘못된 요청입니다", 400),

  // 인증 관련 에러
  UNAUTHENTICATED("A001", "인증이 필요합니다", 401),
  UNAUTHORIZED("A002", "권한이 없습니다", 403),

  // JWT 관련 error code
  EXPIRED_JWT("J001", "만료된 토큰입니다", 401),
  UNSUPPORTED_JWT("J002", "지원하지 않는 토큰입니다", 401),
  MALFORMED_JWT("J003", "잘못된 토큰입니다", 401),
  INVALID_SIGNATURE_JWT("J004", "잘못된 토큰입니다", 401),
  ILLEGAL_ARGUMENT_JWT("J005", "토큰이 비어있거나 잘못되었습니다", 401);


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
