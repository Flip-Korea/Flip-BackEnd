package com.flip.flipapp.domain.post.exception;

import com.flip.flipapp.global.exception.BusinessException;

public class PostNotFoundException extends BusinessException {

  public PostNotFoundException() {
    super(PostErrorCode.POST_NOT_FOUND);
  }
}
