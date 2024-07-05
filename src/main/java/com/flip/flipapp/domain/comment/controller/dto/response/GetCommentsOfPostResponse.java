package com.flip.flipapp.domain.comment.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flip.flipapp.domain.comment.repository.dto.CommentOfPostDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetCommentsOfPostResponse(
    List<CommentOfPost> comments,
    long totalCount
) {

  @JsonInclude(JsonInclude.Include.NON_NULL) // Null 값 제외
  private record CommentOfPost(
      Long commentId,
      String content,
      LocalDateTime commentAt,
      Long profileId,
      String nickname,
      String profileImageUrl

  ) {

  }

  public static GetCommentsOfPostResponse from(Page<CommentOfPostDto> page) {
    List<CommentOfPost> comments = page.getContent().stream()
                                       .map(content ->
                                           new CommentOfPost(
                                               content.getCommentId(),
                                               content.getContent(),
                                               content.getCommentAt(),
                                               content.getProfileId(),
                                               content.getNickname(),
                                               content.getProfileImageUrl()
                                           ))
                                       .toList();
    return new GetCommentsOfPostResponse(comments, page.getTotalElements());
  }
}
