package com.flip.flipapp.domain.post.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class NotPostWriterException extends BusinessException {

  public NotPostWriterException() {
    super(PostErrorCode.NOT_POST_WRITER);
  }
}
