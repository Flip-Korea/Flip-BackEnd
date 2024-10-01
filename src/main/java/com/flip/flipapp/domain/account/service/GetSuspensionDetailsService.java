package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import com.flip.flipapp.domain.account.controller.dto.response.GetSuspensionDetailsResponse;
import com.flip.flipapp.domain.account.exception.AccountNotFoundException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.blame.repository.BlameRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSuspensionDetailsService {

  private final AccountRepository accountRepository;
  private final BlameRepository blameRepository;

  public GetSuspensionDetailsResponse getSuspensionDetails(OauthIdRequest oauthIdRequest) {
    String fullOauthId = oauthIdRequest.provider() + oauthIdRequest.oauthId();

    Account account = accountRepository.findByOauthId(fullOauthId)
        .orElseThrow(AccountNotFoundException::new);

    List<String> distinctBlameTypes = blameRepository.findDistinctBlameTypesByReportedId(account);

    return new GetSuspensionDetailsResponse(
        account.getSuspendedAt(),
        account.getAccountState().getDescription(),
        distinctBlameTypes
    );
  }
}
