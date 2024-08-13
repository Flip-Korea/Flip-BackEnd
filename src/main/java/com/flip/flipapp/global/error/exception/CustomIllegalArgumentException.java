package com.flip.flipapp.global.error.exception;

import com.flip.flipapp.global.error.CommonErrorCode;

public class CustomIllegalArgumentException extends BusinessException {

  public CustomIllegalArgumentException() {
    super(CommonErrorCode.ILLEGAL_ARGUMENT_JWT);
  }
}
