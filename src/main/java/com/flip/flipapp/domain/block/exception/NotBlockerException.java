package com.flip.flipapp.domain.block.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class NotBlockerException extends BusinessException {

  public NotBlockerException() {
    super(BlockErrorCode.NOT_BLOCKER);
  }
}
