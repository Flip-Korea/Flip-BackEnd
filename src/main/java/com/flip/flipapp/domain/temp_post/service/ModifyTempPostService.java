package com.flip.flipapp.domain.temp_post.service;

import com.flip.flipapp.domain.category.exception.CategoryNotFoundException;
import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.category.repository.CategoryRepository;
import com.flip.flipapp.domain.temp_post.exception.NotTempPostWriterException;
import com.flip.flipapp.domain.temp_post.exception.TempPostNotFoundException;
import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.repository.TempPostRepository;
import com.flip.flipapp.domain.temp_post.service.dto.ModifyTempPostCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ModifyTempPostService {

  private final TempPostRepository tempPostRepository;
  private final CategoryRepository categoryRepository;

  @Transactional
  public void modifyTempPost(ModifyTempPostCommand command) {
    TempPost existingTempPost = findTempPost(command.tempPostId());

    if (!existingTempPost.isWriter(command.profileId())) {
      throw new NotTempPostWriterException();
    }

    TempPost newTempPost;
    if (isNewCategory(existingTempPost.getCategory().getCategoryId(), command.categoryId())) {
      Category newCategory = findCategory(command.categoryId());
      newTempPost = command.toTempPost(newCategory);
    } else {
      newTempPost = command.toTempPost(existingTempPost.getCategory());
    }

    existingTempPost.modifyTo(newTempPost);
  }

  public boolean isNewCategory(Long categoryId, Long newCategoryId) {
    return !categoryId.equals(newCategoryId);
  }

  public TempPost findTempPost(Long tempPostId) {
    return tempPostRepository.findById(tempPostId).orElseThrow(TempPostNotFoundException::new);
  }

  private Category findCategory(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
  }
}
