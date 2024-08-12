package com.flip.flipapp.domain.scrap.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ScrapPageDto {

  private Long scrapId;
  private LocalDateTime scrapAt;
  private String postTitle;
  private String postContent;
  private String postWriterNickname;

  @QueryProjection
  public ScrapPageDto(Long scrapId, LocalDateTime scrapAt, String postTitle, String postContent
      , String postWriterNickname) {
    this.scrapId = scrapId;
    this.scrapAt = scrapAt;
    this.postTitle = postTitle;
    this.postContent = postContent;
    this.postWriterNickname = postWriterNickname;
  }
}
