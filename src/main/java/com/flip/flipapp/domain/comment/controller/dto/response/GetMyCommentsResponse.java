package com.flip.flipapp.domain.comment.controller.dto.response;

import com.flip.flipapp.domain.comment.repository.dto.MyCommentPageDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetMyCommentsResponse(
    List<MyComment> comments,
    long totalCount
) {

  public record MyComment(
      Long postId,
      String postWriterNickname,
      String postTitle,
      Long commentId,
      String commentContent,
      LocalDateTime commentAt
  ) {

  }

  public static GetMyCommentsResponse from(Page<MyCommentPageDto> page) {
    List<MyComment> myComments = page.getContent().stream()
                                     .map(content -> new MyComment(
                                         content.getPostId(),
                                         content.getPostWriterNickname(),
                                         content.getPostTitle(),
                                         content.getCommentId(),
                                         content.getCommentContent(),
                                         content.getCommentAt()
                                     ))
                                     .toList();

    return new GetMyCommentsResponse(
        myComments,
        page.getTotalElements()
    );
  }
}
