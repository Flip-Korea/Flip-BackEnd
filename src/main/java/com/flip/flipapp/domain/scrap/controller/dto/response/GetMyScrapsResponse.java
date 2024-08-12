package com.flip.flipapp.domain.scrap.controller.dto.response;

import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetMyScrapsResponse(
    List<MyScrap> scraps,
    long totalCount
) {

  public record MyScrap(
      Long scrapId,
      LocalDateTime scrapAt,
      String postTitle,
      String postContent,
      String postWriterNickname
  ) {

  }

  public static GetMyScrapsResponse from(Page<ScrapPageDto> page) {
    List<MyScrap> scraps = page.getContent().stream()
                               .map(content ->
                                   new MyScrap(
                                       content.getScrapId(),
                                       content.getScrapAt(),
                                       content.getPostTitle(),
                                       content.getPostContent(),
                                       content.getPostWriterNickname())
                               )
                               .toList();

    return new GetMyScrapsResponse(scraps, page.getTotalElements());
  }
}
