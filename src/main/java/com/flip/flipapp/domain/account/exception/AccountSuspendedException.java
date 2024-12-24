package com.flip.flipapp.domain.account.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class AccountSuspendedException extends BusinessException {

  public AccountSuspendedException() {
    super(AccountErrorCode.ACCOUNT_SUSPENDED);
  }

}
