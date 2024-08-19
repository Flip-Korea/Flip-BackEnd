package com.flip.flipapp.domain.follow.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class FollowNotFoundException extends BusinessException {

  public FollowNotFoundException() {
    super(FollowErrorCode.FOLLOW_NOT_FOUND);
  }
}
