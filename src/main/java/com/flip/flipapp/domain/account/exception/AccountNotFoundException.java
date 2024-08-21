package com.flip.flipapp.domain.account.exception;


import com.flip.flipapp.global.error.exception.BusinessException;

public class AccountNotFoundException extends BusinessException {

  public AccountNotFoundException() {
    super(AccountErrorCode.ACCOUNT_NOT_FOUND);
  }

}
