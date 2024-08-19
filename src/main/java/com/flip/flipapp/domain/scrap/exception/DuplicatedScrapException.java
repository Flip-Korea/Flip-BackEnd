package com.flip.flipapp.domain.scrap.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class DuplicatedScrapException extends BusinessException {
  public DuplicatedScrapException() {
    super(ScrapErrorCode.DUPLICATED_SCRAP);
  }

}
