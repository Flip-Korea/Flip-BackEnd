package com.flip.flipapp.domain.interestCategory;

import com.flip.flipapp.domain.category.Category;
import com.flip.flipapp.domain.profile.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interest_category")
@Getter
@NoArgsConstructor
public class InterestCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "interest_category_id", nullable = false, columnDefinition = "bigint")
  private Long interestCategoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, columnDefinition = "bigint")
  private Profile profileId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false, columnDefinition = "bigint")
  private Category categoryId;

}