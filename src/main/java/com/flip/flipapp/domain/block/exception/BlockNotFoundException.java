package com.flip.flipapp.domain.block.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class BlockNotFoundException extends BusinessException {

  public BlockNotFoundException() {
    super(BlockErrorCode.BLOCK_NOT_FOUND);
  }
}
