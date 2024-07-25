package com.flip.flipapp.domain.temp_post.service.dto;

import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import java.util.List;

public record ModifyTempPostCommand(
    Long tempPostId,
    String title,
    String content,
    BgColorType bgColorType,
    FontStyleType fontStyleType,
    Long categoryId,
    List<String> tags,
    Long profileId
) {

  public TempPost toTempPost(Category category) {
    return TempPost.builder()
                   .title(title)
                   .content(content)
                   .bgColor(bgColorType)
                   .fontStyle(fontStyleType)
                   .category(category)
                   .tags(tags)
                   .build();
  }
}
