package com.flip.flipapp.domain.profile.service;

import com.flip.flipapp.domain.profile.exception.ProfileDuplicateUserIdException;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckUserIdService {

  private final ProfileRepository profileRepository;

  public void checkUserId(String userId) {
    if (profileRepository.existsByUserId(userId)) {
      throw new ProfileDuplicateUserIdException();
    }
  }

}
