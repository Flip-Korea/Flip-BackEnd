package com.flip.flipapp.domain.follow.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class SelfFollowException extends BusinessException {

  public SelfFollowException() {
    super(FollowErrorCode.SELF_FOLLOW);
  }
}
