package com.flip.flipapp.domain.post.service.dto;

import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.post.model.Post;
import java.util.List;

public record ModifyPostCommand(
    Long postId,
    String title,
    String content,
    BgColorType bgColorType,
    FontStyleType fontStyleType,
    List<String> newTags,
    List<Long> deletePostTagIds,
    Long categoryId,
    Long profileId
) {

  public Post toPost(Category category){
    return Post.builder()
               .title(title)
               .content(content)
               .bgColor(bgColorType)
               .fontStyle(fontStyleType)
               .category(category)
               .build();
  }
}
