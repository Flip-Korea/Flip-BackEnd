package com.flip.flipapp.domain.tempPost.model;

public enum BgColorType {
  RED("Red"),
  GREEN("Green"),
  BLUE("Blue"),
  YELLOW("Yellow");

  private final String description;

  BgColorType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}
