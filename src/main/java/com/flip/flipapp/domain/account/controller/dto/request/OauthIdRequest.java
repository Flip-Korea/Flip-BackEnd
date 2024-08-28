package com.flip.flipapp.domain.account.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record OauthIdRequest(
    @NotBlank @Pattern(regexp = "google|kakao|naver|apple", message = "잘못된 소셜 로그인 공급자입니다.")
    String provider,
    @NotBlank
    String oauthId
) {

}
