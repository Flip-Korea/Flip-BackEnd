package com.flip.flipapp.domain.scrap.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class SelfScrapException extends BusinessException {

  public SelfScrapException() {
    super(ScrapErrorCode.SELF_SCRAP);
  }

}
