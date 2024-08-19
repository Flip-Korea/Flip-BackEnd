package com.flip.flipapp.global.error.exception;

import com.flip.flipapp.global.error.CommonErrorCode;

public class CustomMalformedJwtException extends BusinessException {

  public CustomMalformedJwtException() {
    super(CommonErrorCode.MALFORMED_JWT);
  }
}
