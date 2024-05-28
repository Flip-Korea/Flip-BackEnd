package com.flip.flipapp.domain.notification;

public enum NotiType {
  LIKE("좋아요"),
  COMMENT("댓글"),
  FOLLOW("팔로우");

  private final String description;

  NotiType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}

