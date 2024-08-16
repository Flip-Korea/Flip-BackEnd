package com.flip.flipapp.domain.profile.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class ProfileNotFoundException extends BusinessException {

  public ProfileNotFoundException() {
    super(ProfileErrorCode.PROFILE_NOT_FOUND);
  }
}
