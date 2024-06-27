package com.flip.flipapp.domain.interest_category.service;

import java.util.Set;

public record ChangeMyCategoriesCommand(
    Long profileId,
    Set<Long> categoryIds
) {

}
