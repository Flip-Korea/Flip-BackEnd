package com.flip.flipapp.domain.account.controller.dto.response;

import com.flip.flipapp.domain.account.model.Account;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record AccountSuspendedResponse(
    LocalDateTime suspendedAt,
    String accountState,
    List<String> blameTypes
) {

  public static AccountSuspendedResponse from(Account account, List<String> blameTypes) {
    return AccountSuspendedResponse.builder()
        .suspendedAt(account.getSuspendedAt())
        .accountState(account.getAccountState().getDescription())
        .blameTypes(blameTypes)
        .build();
  }

}
