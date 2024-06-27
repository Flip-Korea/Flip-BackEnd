package com.flip.flipapp.domain.interest_category.service;

import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import com.flip.flipapp.domain.interest_category.repository.InterestCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMyCategoriesService {

  private final InterestCategoryRepository interestCategoryRepository;

  public List<InterestCategory> getMyCategories(Long profileId) {
    return interestCategoryRepository.findAllByProfileId(profileId);
  }
}
