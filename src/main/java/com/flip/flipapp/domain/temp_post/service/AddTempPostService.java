package com.flip.flipapp.domain.temp_post.service;

import com.flip.flipapp.domain.category.exception.CategoryNotFoundException;
import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.category.repository.CategoryRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.repository.TempPostRepository;
import com.flip.flipapp.domain.temp_post.service.dto.AddTempPostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddTempPostService {

  private final CategoryRepository categoryRepository;
  private final TempPostRepository tempPostRepository;

  @Transactional
  public TempPost addTempPost(AddTempPostCommand command) {
    TempPost newTempPost = TempPost.builder()
                                   .title(command.title())
                                   .content(command.content())
                                   .fontStyle(command.fontStyleType())
                                   .bgColor(command.bgColorType())
                                   .profile(Profile.getAuthenticatedProfile(command.profileId()))
                                   .tags(command.tags())
                                   .category(findCategory(command.categoryId()))
                                   .build();

    return tempPostRepository.save(newTempPost);
  }

  private Category findCategory(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
  }
}
