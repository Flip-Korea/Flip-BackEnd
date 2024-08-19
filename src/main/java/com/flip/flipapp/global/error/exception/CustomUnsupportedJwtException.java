package com.flip.flipapp.global.error.exception;

import com.flip.flipapp.global.error.CommonErrorCode;

public class CustomUnsupportedJwtException extends BusinessException {

  public CustomUnsupportedJwtException() {
    super(CommonErrorCode.UNSUPPORTED_JWT);
  }
}
