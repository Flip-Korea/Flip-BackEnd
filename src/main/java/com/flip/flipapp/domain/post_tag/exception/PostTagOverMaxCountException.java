package com.flip.flipapp.domain.post_tag.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class PostTagOverMaxCountException extends BusinessException {

  public PostTagOverMaxCountException() {
    super(PostTagErrorCode.OVER_MAX_COUNT);
  }
}
