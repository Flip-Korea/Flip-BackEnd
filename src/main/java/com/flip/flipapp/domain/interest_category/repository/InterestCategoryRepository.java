package com.flip.flipapp.domain.interest_category.repository;

import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterestCategoryRepository extends JpaRepository<InterestCategory, Long> {

  @Query("select ic from InterestCategory ic join fetch ic.category where ic.profile.profileId = :profileId")
  List<InterestCategory> findAllByProfileId(Long profileId);
}
