package com.flip.flipapp.domain.blame.model;

public enum BlameType {
  DISLIKE("마음에 들지 않음"),
  SPAM_OR_ADVERTISEMENT("스팸 및 광고성 게시글"),
  INAPPROPRIATE("부적절함"),
  FRAUD_OR_FALSE("사기 또는 거짓"),
  HATE_SPEECH("혐오 발언");

  private final String description;

  BlameType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}