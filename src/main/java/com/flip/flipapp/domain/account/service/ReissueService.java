package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.exception.AccountNotFoundException;
import com.flip.flipapp.domain.account.exception.AccountSuspendedException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

  private final AccountRepository accountRepository;

  public Long reissue(Long profileId) {
    Account account = accountRepository.findById(profileId)
        .orElseThrow(AccountNotFoundException::new);

    if (!account.isAccountActive()) {
      throw new AccountSuspendedException();
    }

    return profileId;
  }

}
