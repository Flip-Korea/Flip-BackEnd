package com.flip.flipapp.domain.post.controller.dto.request;

import com.flip.flipapp.domain.post.model.BgColorType;
import com.flip.flipapp.domain.post.model.FontStyleType;
import com.flip.flipapp.domain.post.service.dto.AddPostCommand;
import com.flip.flipapp.global.validation.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record AddPostRequest(
    @NotBlank
    String title,
    @NotBlank
    String content,
    @EnumValue(target = BgColorType.class)
    String bgColorType,
    @EnumValue(target = FontStyleType.class)
    String fontStyleType,
    @NotNull
    @Size(max = 10)
    List<String> tags,
    @NotNull
    @Min(1)
    Long categoryId
) {

  public AddPostCommand toCommand(Long profileId) {
    return new AddPostCommand(title, content, BgColorType.valueOf(bgColorType),
        FontStyleType.valueOf(fontStyleType), categoryId, tags, profileId);
  }
}
