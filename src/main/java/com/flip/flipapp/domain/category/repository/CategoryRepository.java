package com.flip.flipapp.domain.category.repository;

import com.flip.flipapp.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
