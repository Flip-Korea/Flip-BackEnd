package com.flip.flipapp.domain.interest_category.controller.dto.request;

import com.flip.flipapp.domain.interest_category.service.ChangeMyCategoriesCommand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ChangeMyCategoriesRequest(
    @NotNull
    @Size(min = 3, max = 12)
    Set<Long> categoryIds
) {

  public ChangeMyCategoriesCommand toCommand(Long profileId) {
    return new ChangeMyCategoriesCommand(profileId, categoryIds);
  }
}
