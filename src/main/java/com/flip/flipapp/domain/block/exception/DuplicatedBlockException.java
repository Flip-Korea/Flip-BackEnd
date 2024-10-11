package com.flip.flipapp.domain.block.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class DuplicatedBlockException extends BusinessException {

  public DuplicatedBlockException() {
    super(BlockErrorCode.DUPLICATED_BLOCK);
  }
}
