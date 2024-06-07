package com.flip.flipapp.docs;

public enum ErrorFieldDescription {
  MESSAGE("message", "에러 메세지"),
  CODE("code", "팀에서 정의한 에러코드"),
  ERROR_LIST_FIELD("errors[].field", "문제있는 필드"),
  ERROR_LIST_VALUE("errors[].value", "문제있는 값"),
  ERROR_LIST_REASON("errors[].reason", "문제 원인");

  private String field;
  private String description;

  ErrorFieldDescription(String field, String description) {
    this.field = field;
    this.description = description;
  }

  public String getField() {
    return field;
  }

  public String getDescription() {
    return description;
  }
}
