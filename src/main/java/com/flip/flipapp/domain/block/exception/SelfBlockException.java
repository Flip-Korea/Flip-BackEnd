package com.flip.flipapp.domain.block.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class SelfBlockException extends BusinessException {

  public SelfBlockException() {
    super(BlockErrorCode.SELF_BLOCK);
  }
}
