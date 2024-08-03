package com.flip.flipapp.domain.scrap.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class ScrapNotFoundException extends BusinessException {

  public ScrapNotFoundException() {
    super(ScrapErrorCode.SCRAP_NOT_FOUND);
  }
}
