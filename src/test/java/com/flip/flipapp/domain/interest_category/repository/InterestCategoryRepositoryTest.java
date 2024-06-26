package com.flip.flipapp.domain.interest_category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InterestCategoryRepositoryTest {

  @Autowired
  InterestCategoryRepository interestCategoryRepository;

  final Long profileId = 1L;

  @Test
  @Sql("findAllByProfileId.sql")
  void should_return_interest_categories_when_they_exist() {
    List<InterestCategory> interestCategories = interestCategoryRepository.findAllByProfileId(
        profileId);

    assertThat(interestCategories).hasSize(2);
  }
}