package com.flip.flipapp.domain.account.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RegisterRequest(
    @NotBlank String oauthId,
    @NotNull @Size(min = 3, max = 12) List<Long> categories,
    @Valid UserProfile profile
) {

  public record UserProfile(
      @NotBlank String userId,
      @NotBlank String nickname,
      String photoUrl
  ) {

  }
}

