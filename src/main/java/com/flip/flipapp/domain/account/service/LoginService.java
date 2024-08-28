package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import com.flip.flipapp.domain.account.exception.AccountNotFoundException;
import com.flip.flipapp.domain.account.exception.AccountSuspendedException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final AccountRepository accountRepository;
  private final ProfileRepository profileRepository;

  public Profile login(OauthIdRequest oauthIdRequest) {

    Account account = accountRepository.findByOauthId(
            oauthIdRequest.provider() + oauthIdRequest.oauthId())
        .orElseThrow(AccountNotFoundException::new);

    if (!account.isAccountActive()) {
      throw new AccountSuspendedException();
    }

    Profile RecentProfile = profileRepository.findById(account.getRecentLogin())
        .orElseThrow(ProfileNotFoundException::new);

    return RecentProfile;
  }

}
