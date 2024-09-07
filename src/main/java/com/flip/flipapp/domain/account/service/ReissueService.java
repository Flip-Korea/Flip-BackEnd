package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.exception.AccountNotFoundException;
import com.flip.flipapp.domain.account.exception.AccountSuspendedException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

  private final JwtProvider jwtProvider;
  private final AccountRepository accountRepository;

  public JwtResponse reissue(Long profileId) {
    Account account = accountRepository.findById(profileId)
        .orElseThrow(AccountNotFoundException::new);

    if (!account.isAccountActive()) {
      throw new AccountSuspendedException();
    }

    String accessToken = jwtProvider.createAccessToken(profileId);
    String refreshToken = jwtProvider.createRefreshToken(profileId);

    return new JwtResponse(accessToken, refreshToken);
  }

}
