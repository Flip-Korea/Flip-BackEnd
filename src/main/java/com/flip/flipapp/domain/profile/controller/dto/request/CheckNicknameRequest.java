package com.flip.flipapp.domain.profile.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CheckNicknameRequest(
    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]{2,12}$", message = "2~12자의 한글, 영문, 숫자만 사용 가능합니다.")
    String nickname
) {
}
