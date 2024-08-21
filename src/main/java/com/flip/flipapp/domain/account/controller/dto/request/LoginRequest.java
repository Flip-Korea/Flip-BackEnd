package com.flip.flipapp.domain.account.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String provider,
    @NotBlank String oauthId
) {

}
