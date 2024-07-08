package com.flip.flipapp.domain.profile.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class ProfileDuplicateUserIdException extends BusinessException {

  public ProfileDuplicateUserIdException() {
    super(ProfileErrorCode.PROFILE_DUPLICATE_USER_ID);
  }

}