package com.flip.flipapp.domain.tempPost.model;

public enum FontStyleType {
  BOLD("Bold"),
  ITALIC("Italic"),
  UNDERLINE("Underline"),
  NORMAL("Normal");

  private final String description;

  FontStyleType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }

}
