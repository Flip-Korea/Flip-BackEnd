package com.flip.flipapp.global.error.exception;

import com.flip.flipapp.global.error.CommonErrorCode;

public class CustomExpiredJwtException extends BusinessException {

  public CustomExpiredJwtException() {
    super(CommonErrorCode.EXPIRED_JWT);
  }
}
