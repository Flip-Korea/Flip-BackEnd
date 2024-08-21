package com.flip.flipapp.domain.scrap.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ScrapPageDto {

  private Long scrapId;
  private String scrapComment;
  private LocalDateTime scrapAt;
  private String postTitle;
  private String postWriterNickname;

  @QueryProjection
  public ScrapPageDto(Long scrapId, String scrapComment, LocalDateTime scrapAt, String postTitle,
      String postWriterNickname) {
    this.scrapId = scrapId;
    this.scrapComment = scrapComment;
    this.scrapAt = scrapAt;
    this.postTitle = postTitle;
    this.postWriterNickname = postWriterNickname;
  }
}
