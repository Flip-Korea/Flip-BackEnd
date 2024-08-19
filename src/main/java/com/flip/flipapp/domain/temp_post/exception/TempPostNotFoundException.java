package com.flip.flipapp.domain.temp_post.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class TempPostNotFoundException extends BusinessException {

  public TempPostNotFoundException() {
    super(TempPostErrorCode.TEMP_POST_NOT_FOUND);
  }

}
