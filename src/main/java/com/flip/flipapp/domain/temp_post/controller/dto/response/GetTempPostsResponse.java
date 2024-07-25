package com.flip.flipapp.domain.temp_post.controller.dto.response;

import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

public record GetTempPostsResponse(
    List<TempPostResponse> tempPosts,
    long totalCount
) {

  private record TempPostResponse(
      Long tempPostId,
      String title,
      String content,
      BgColorType bgColorType,
      FontStyleType fontStyleType,
      LocalDateTime postAt,
      Long categoryId,
      String categoryName,
      List<String> tags
  ) {

  }

  public static GetTempPostsResponse from(Page<TempPost> page) {
    List<TempPostResponse> tempPosts = page.getContent().stream()
                                           .map(content -> new TempPostResponse(
                                               content.getPostId(),
                                               content.getTitle(),
                                               content.getContent(),
                                               content.getBgColor(),
                                               content.getFontStyle(),
                                               content.getPostAt(),
                                               content.getCategory().getCategoryId(),
                                               content.getCategory().getCategoryName().toString(),
                                               content.getTags()
                                           ))
                                           .toList();

    return new GetTempPostsResponse(tempPosts, page.getTotalElements());
  }
}
