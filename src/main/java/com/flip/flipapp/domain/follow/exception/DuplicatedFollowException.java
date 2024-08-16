package com.flip.flipapp.domain.follow.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class DuplicatedFollowException extends BusinessException {

  public DuplicatedFollowException() {
    super(FollowErrorCode.DUPLICATED_FOLLOW);
  }
}
