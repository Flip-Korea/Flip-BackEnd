package com.flip.flipapp.domain.temp_post.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class NotTempPostWriterException extends BusinessException {

  public NotTempPostWriterException() {
    super(TempPostErrorCode.NOT_TEMP_POST_WRITER);
  }
}
