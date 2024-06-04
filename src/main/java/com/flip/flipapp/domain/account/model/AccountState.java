package com.flip.flipapp.domain.account.model;

public enum AccountState {
  ACTIVE(0, "활성"),
  SUSPENDED_5_DAYS(5, "5일 정지"),
  SUSPENDED_10_DAYS(10, "10일 정지"),
  SUSPENDED_15_DAYS(15, "15일 정지"),
  SUSPENDED_INDEFINITELY(-1, "영구 정지");

  private final int suspensionDays;
  private final String description;

  AccountState(int suspensionDays, String description) {
    this.suspensionDays = suspensionDays;
    this.description = description;
  }

  public int getSuspensionDays() {
    return suspensionDays;
  }

  public String getDescription() {
    return description;
  }
}