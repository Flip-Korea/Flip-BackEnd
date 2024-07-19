package com.flip.flipapp.domain.temp_post.service.dto;

import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import java.util.List;

public record AddTempPostCommand(
    String title,
    String content,
    BgColorType bgColorType,
    FontStyleType fontStyleType,
    Long categoryId,
    List<String> tags,
    Long profileId
) {

}
