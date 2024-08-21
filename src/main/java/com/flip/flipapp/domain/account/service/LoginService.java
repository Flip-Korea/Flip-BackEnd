package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.exception.AccountNotFoundException;
import com.flip.flipapp.domain.account.exception.AccountSuspendedException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.model.AccountState;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.blame.repository.BlameRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final AccountRepository accountRepository;
  private final ProfileRepository profileRepository;
  private final BlameRepository blameRepository;

  public Profile login(String provider, String oauthId)
      throws AccountNotFoundException, AccountSuspendedException {
    Account account = findAccount(provider + oauthId);
    checkAccountState(account);
    return profileRepository.findByAccount(account);
  }

  private Account findAccount(String fullOauthId) {
    return accountRepository.findByOauthId(fullOauthId)
        .orElseThrow(AccountNotFoundException::new);
  }

  private void checkAccountState(Account account) {
    if (account.getAccountState() != AccountState.ACTIVE) {
      LocalDateTime suspendedAt = account.getSuspendedAt();
      String accountState = account.getAccountState().getDescription();
      List<String> blameTypes = blameRepository.findByReportedId(account)
          .stream()
          .map(blame -> blame.getType().getDescription())
          .distinct()
          .collect(Collectors.toList());
      throw new AccountSuspendedException(suspendedAt, accountState, blameTypes);
    }
  }

}
