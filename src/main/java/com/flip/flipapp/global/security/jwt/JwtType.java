package com.flip.flipapp.global.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtType {
  ACCESS_TOKEN("access", 1000L * 60 * 60 * 2),
  REFRESH_TOKEN("refresh", 1000L * 60 * 60 * 24 * 14);

  private final String type;
  private final Long expiration;

}
