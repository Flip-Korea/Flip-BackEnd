package com.flip.flipapp.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HeaderConstants {
  ACCESS_TOKEN("Authorization", "Bearer access-token"),
  REFRESH_TOKEN("Authorization", "Bearer refresh-token");

  private final String key;
  private final String value;

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }
}
