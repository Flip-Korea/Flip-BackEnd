package com.flip.flipapp.domain.account.exception;

import com.flip.flipapp.global.error.exception.BusinessException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class AccountSuspendedException extends BusinessException {

  private final LocalDateTime suspendedAt;
  private final String accountState;
  private final List<String> blameTypes;

  public AccountSuspendedException(LocalDateTime suspendedAt, String accountState, List<String> blameTypes) {
    super(AccountErrorCode.ACCOUNT_SUSPENDED);
    this.suspendedAt = suspendedAt;
    this.accountState = accountState;
    this.blameTypes = blameTypes;
  }

}
