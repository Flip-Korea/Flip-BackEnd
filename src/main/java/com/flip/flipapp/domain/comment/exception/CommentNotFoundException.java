package com.flip.flipapp.domain.comment.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {

  public CommentNotFoundException() {
    super(CommentErrorCode.COMMENT_NOT_FOUND);
  }
}
