package com.flip.flipapp.global.error.exception;

import com.flip.flipapp.global.error.CommonErrorCode;

public class CustomSignatureException extends BusinessException {

  public CustomSignatureException() {
    super(CommonErrorCode.INVALID_SIGNATURE_JWT);
  }
}
