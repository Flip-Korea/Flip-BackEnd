package com.flip.flipapp.domain.interest_category.service;

import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.interest_category.exception.InterestCategoryRelationException;
import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import com.flip.flipapp.domain.interest_category.repository.InterestCategoryRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangeMyCategoriesService {

  private final InterestCategoryRepository interestCategoryRepository;

  @Transactional
  public void changeMyCategories(ChangeMyCategoriesCommand command) {
    List<InterestCategory> interestCategories = interestCategoryRepository.findAllByProfileId(
        command.profileId());

    Set<Long> existingCategories = interestCategories.stream()
                                                     .map(ic -> ic.getCategory().getCategoryId())
                                                     .collect(Collectors.toSet());

    // 필요없는 기존의 관심 카테고리 삭제
    List<InterestCategory> categoriesToDelete = interestCategories.stream()
                                                                  .filter(ic ->
                                                                      !command.categoryIds()
                                                                              .contains(
                                                                                  ic.getCategory()
                                                                                    .getCategoryId())
                                                                  )
                                                                  .toList();
    interestCategoryRepository.deleteAll(categoriesToDelete);

    // 추가해야할 새로운 카테고리 추가
    List<InterestCategory> categoriesToAdd = command.categoryIds().stream()
                                                    .filter(id -> !existingCategories.contains(id))
                                                    .map(id ->
                                                        InterestCategory.builder()
                                                                        .category(
                                                                            Category.builder()
                                                                                    .categoryId(id)
                                                                                    .build())
                                                                        .profile(
                                                                            Profile.getAuthenticatedProfile(
                                                                                command.profileId()))
                                                                        .build()
                                                    )
                                                    .toList();
    try {
      interestCategoryRepository.saveAll(categoriesToAdd);
    } catch (DataIntegrityViolationException e) {
      throw new InterestCategoryRelationException();
    }
  }
}
