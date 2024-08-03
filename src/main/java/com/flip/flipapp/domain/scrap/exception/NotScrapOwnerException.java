package com.flip.flipapp.domain.scrap.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class NotScrapOwnerException extends BusinessException {

  public NotScrapOwnerException() {
    super(ScrapErrorCode.NOT_SCRAP_OWNER);
  }
}
