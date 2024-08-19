package com.flip.flipapp.domain.comment.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class InvalidPostOfCommentException extends BusinessException {

  public InvalidPostOfCommentException() {
    super(CommentErrorCode.INVALID_POST_OF_COMMENT);
  }
}
