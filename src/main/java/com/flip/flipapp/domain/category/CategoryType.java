package com.flip.flipapp.domain.category;

public enum CategoryType {
  DAILY("일상"),
  IT_SCIENCE("IT과학"),
  TIPS("팁"),
  THOUGHTS("생각");

  private final String description;

  CategoryType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}
