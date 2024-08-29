package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import com.flip.flipapp.domain.account.controller.dto.response.GetSuspensionDetailsResponse;
import com.flip.flipapp.domain.account.exception.AccountNotFoundException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.blame.repository.BlameRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSuspensionDetailsService {

  private final AccountRepository accountRepository;
  private final BlameRepository blameRepository;

  public GetSuspensionDetailsResponse getSuspensionDetails(OauthIdRequest oauthIdRequest) {
    Account account = accountRepository.findByOauthId(
            oauthIdRequest.provider() + oauthIdRequest.oauthId())
        .orElseThrow(AccountNotFoundException::new);

    List<String> blameTypes = blameRepository.findByReportedId(account)
        .stream()
        .map(blame -> blame.getType().getDescription())
        .distinct()
        .collect(Collectors.toList());

    return new GetSuspensionDetailsResponse(
        account.getSuspendedAt(),
        account.getAccountState().getDescription(),
        blameTypes
    );
  }
}
