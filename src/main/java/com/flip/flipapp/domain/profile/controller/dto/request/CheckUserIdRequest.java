package com.flip.flipapp.domain.profile.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CheckUserIdRequest(
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._]{4,16}$", message = "4~16자의 영문, 숫자, 특수기호(_),(.)만 사용 가능합니다.")
    String userId
) {
}
