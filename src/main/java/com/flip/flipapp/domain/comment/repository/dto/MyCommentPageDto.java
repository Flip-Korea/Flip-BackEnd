package com.flip.flipapp.domain.comment.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyCommentPageDto {

  private Long postId;
  private String postWriterNickname;
  private String postTitle;
  private Long commentId;
  private String commentContent;
  private LocalDateTime commentAt;

  @QueryProjection
  public MyCommentPageDto(Long postId, String postWriterNickname, String postTitle, Long commentId,
      String commentContent, LocalDateTime commentAt) {
    this.postId = postId;
    this.postWriterNickname = postWriterNickname;
    this.postTitle = postTitle;
    this.commentId = commentId;
    this.commentContent = commentContent;
    this.commentAt = commentAt;
  }
}
