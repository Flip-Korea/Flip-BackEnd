package com.flip.flipapp.domain.category.service;

import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesService {

  private final CategoryRepository categoryRepository;

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
}
