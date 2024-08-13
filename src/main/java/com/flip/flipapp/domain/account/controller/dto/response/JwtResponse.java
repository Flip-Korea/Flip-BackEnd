package com.flip.flipapp.domain.account.controller.dto.response;

public record JwtResponse(
    String accessToken,
    String refreshToken
) {

}
