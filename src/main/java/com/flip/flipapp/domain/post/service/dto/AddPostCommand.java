package com.flip.flipapp.domain.post.service.dto;

import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import java.util.List;

public record AddPostCommand(
    String title,
    String content,
    BgColorType bgColorType,
    FontStyleType fontStyleType,
    Long categoryId,
    List<String> tags,
    Long profileId
) {

}
