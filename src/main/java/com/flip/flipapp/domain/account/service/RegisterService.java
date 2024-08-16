package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.interest_category.exception.InterestCategoryRelationException;
import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import com.flip.flipapp.domain.interest_category.repository.InterestCategoryRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import com.flip.flipapp.domain.profileImage.model.ProfileImage;
import com.flip.flipapp.domain.profileImage.repository.ProfileImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final AccountRepository accountRepository;
  private final ProfileRepository profileRepository;
  private final ProfileImageRepository profileImageRepository;
  private final InterestCategoryRepository interestCategoryRepository;

  @Transactional
  public Profile register(RegisterRequest registerRequest) {
    Account account = Account.builder()
        .oauthId(registerRequest.oauthId())
        .recentLogin(null)
        .build();
    account = accountRepository.save(account);

    Profile profile = Profile.builder()
        .userId(registerRequest.profile().userId())
        .nickname(registerRequest.profile().nickname())
        .introduce("")
        .account(account)
        .build();
    profileRepository.save(profile);

    account.setRecentLogin(profile.getProfileId());

    if (StringUtils.hasText(registerRequest.profile().photoUrl())) {
      ProfileImage profileImage = ProfileImage.builder()
          .imageUrl(registerRequest.profile().photoUrl())
          .profile(profile)
          .build();
      profileImageRepository.save(profileImage);

    }

    List<InterestCategory> categoriesToAdd = registerRequest.categories().stream()
        .map(categoryId ->
            InterestCategory.builder()
                .profile(profile)
                .category(Category.builder().categoryId(categoryId).build())
                .build()
        )
        .toList();
    try {
      interestCategoryRepository.saveAll(categoriesToAdd);
    } catch (DataIntegrityViolationException e) {
      throw new InterestCategoryRelationException();
    }

    return profile;
  }
}
