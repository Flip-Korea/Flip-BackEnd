package com.flip.flipapp.domain.comment.exception;

import com.flip.flipapp.global.error.exception.BusinessException;

public class NotCommentWriterException extends BusinessException {

  public NotCommentWriterException() {
    super(CommentErrorCode.NOT_COMMENT_WRITER);
  }
}
