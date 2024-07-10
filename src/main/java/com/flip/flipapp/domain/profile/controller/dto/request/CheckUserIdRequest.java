package com.flip.flipapp.domain.profile.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CheckUserIdRequest(
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._]{1,16}$", message = "1-16자리 영문, 숫자, 특수문자(. , _)를 입력해주세요.")
    String userId
) {
}
