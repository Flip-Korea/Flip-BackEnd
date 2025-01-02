package com.flip.flipapp.domain.account.service;

import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.exception.DuplicateOauthIdException;
import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.account.repository.AccountRepository;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import com.flip.flipapp.domain.profileImage.model.ProfileImage;
import com.flip.flipapp.domain.profileImage.repository.ProfileImageRepository;
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

  @Transactional
  public Profile register(RegisterRequest registerRequest) {
    String fullOauthId = registerRequest.provider() + registerRequest.oauthId();

    Account account = Account.builder()
        .oauthId(fullOauthId)
        .adsAgree(registerRequest.adsAgree())
        .recentLogin(null)
        .build();
    try {
      accountRepository.save(account);
    } catch (DataIntegrityViolationException e) {
      throw new DuplicateOauthIdException();
    }

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

    return profile;
  }
}
