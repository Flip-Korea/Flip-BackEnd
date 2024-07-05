package com.flip.flipapp.domain.comment.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentOfPostDto {

  private Long commentId;
  private String content;
  private LocalDateTime commentAt;
  private Long profileId;
  private String nickname;
  private String profileImageUrl;

  @QueryProjection
  public CommentOfPostDto(Long commentId, String content, LocalDateTime commentAt, Long profileId,
      String nickname, String profileImageUrl) {
    this.commentId = commentId;
    this.content = content;
    this.commentAt = commentAt;
    this.profileId = profileId;
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
  }
}
