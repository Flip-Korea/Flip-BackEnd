package com.flip.flipapp.domain.account.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
    @NotBlank @Pattern(regexp = "google|kakao|naver|apple", message = "잘못된 소셜 로그인 공급자입니다.")
    String provider,
    @NotBlank String oauthId,
    @NotNull boolean ads_agree,
    @Valid UserProfile profile
) {

  public record UserProfile(
      @NotBlank String userId,
      @NotBlank String nickname,
      String photoUrl
  ) {

  }
}

