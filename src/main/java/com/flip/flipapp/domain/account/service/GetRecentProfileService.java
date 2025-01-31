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
public class GetRecentProfileService {

  private final AccountRepository accountRepository;
  private final ProfileRepository profileRepository;

  public Profile getRecentProfile(OauthIdRequest oauthIdRequest) {
    String fullOauthId = oauthIdRequest.provider() + oauthIdRequest.oauthId();

    Account account = accountRepository.findByOauthId(fullOauthId)
        .orElseThrow(AccountNotFoundException::new);

    if (!account.isAccountActive()) {
      throw new AccountSuspendedException();
    }

    Profile recentProfile = profileRepository.findById(account.getRecentLogin())
        .orElseThrow(ProfileNotFoundException::new);

    return recentProfile;
  }

}
