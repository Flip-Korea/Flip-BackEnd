package com.flip.flipapp.domain.profile.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CheckNicknameRequest(
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\uAC00-\\uD7A3ㄱ-ㅎㅏ-ㅣ]{2,12}$", message = "2-12자리 한글 및 영문을 입력해주세요.")
    String nickname
) {
}
