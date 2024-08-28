package com.flip.flipapp.domain.account.exception;

import com.flip.flipapp.global.error.exception.BusinessException;
import lombok.Getter;

@Getter
public class AccountSuspendedException extends BusinessException {

  public AccountSuspendedException() {
    super(AccountErrorCode.ACCOUNT_SUSPENDED);
  }

}
