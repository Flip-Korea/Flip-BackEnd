package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.model.AccountState;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.category.model.Category;
import com.flip.flipapp.domain.category.repository.CategoryRepository;
import com.flip.flipapp.domain.interest_category.model.InterestCategory;
import com.flip.flipapp.domain.interest_category.repository.InterestCategoryRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import com.flip.flipapp.domain.profileImage.model.ProfileImage;
import com.flip.flipapp.domain.profileImage.repository.ProfileImageRepository;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final AccountRepository accountRepository;
  private final CategoryRepository categoryRepository;
  private final ProfileRepository profileRepository;
  private final ProfileImageRepository profileImageRepository;
  private final InterestCategoryRepository interestCategoryRepository;
  private final JwtProvider jwtProvider;

  @Transactional
  public JwtResponse register(RegisterRequest registerRequest) {
    Account account = Account.builder()
        .oauthId(registerRequest.oauthId())
        .accountState(AccountState.ACTIVE)
        .recentLogin(-1L)
        .build();

    account = accountRepository.save(account);
    account.setRecentLogin(account.getAccountId());

    accountRepository.save(account);

    Profile profile = Profile.builder()
        .userId(registerRequest.profile().userId())
        .nickname(registerRequest.profile().nickname())
        .introduce(" ")
        .account(account)
        .build();

    profileRepository.save(profile);

    ProfileImage profileImage = ProfileImage.builder()
        .imageUrl(registerRequest.profile().photoUrl())
        .profile(profile)
        .build();

    profileImageRepository.save(profileImage);

    for (Long categoryId : registerRequest.categories()) {
      Category category = categoryRepository.findById(categoryId).orElse(null);

      if (category != null) {
        InterestCategory interestCategory = InterestCategory.builder()
            .profile(profile)
            .category(category)
            .build();
        interestCategoryRepository.save(interestCategory);
      }
    }

    String accessToken = jwtProvider.createToken(profile.getProfileId(),
        jwtProvider.getAccessTokenExpiryTime());
    String refreshToken = jwtProvider.createToken(profile.getProfileId(),
        jwtProvider.getRefreshTokenExpiryTime());

    return new JwtResponse(accessToken, refreshToken);
  }
}
