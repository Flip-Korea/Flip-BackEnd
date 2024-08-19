package com.flip.flipapp.domain.follow.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class NotFollowerException extends BusinessException {

  public NotFollowerException() {
    super(FollowErrorCode.NOT_FOLLOWER);
  }
}
