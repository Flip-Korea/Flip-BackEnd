package com.flip.flipapp.domain.account.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetSuspensionDetailsResponse(
    LocalDateTime suspendedAt,
    String accountState,
    List<String> blameTypes
) {

}