package com.flip.flipapp.domain.account.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class DuplicateOauthIdException extends BusinessException {

  public DuplicateOauthIdException() {
    super(AccountErrorCode.DUPLICATE_OAUTH_ID);
  }
}
