package com.flip.flipapp.domain.comment.service;

import com.flip.flipapp.domain.comment.exception.CommentNotFoundException;
import com.flip.flipapp.domain.comment.model.Comment;
import com.flip.flipapp.domain.comment.repository.CommentRepository;
import com.flip.flipapp.domain.comment.service.dto.DeleteCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCommentService {

  private final CommentRepository commentRepository;

  public void deleteComment(DeleteCommentCommand command) {
    Comment comment = findComment(command.commentId());

    comment.checkPostRelation(command.postId());
    comment.checkWriter(command.userId());

    commentRepository.delete(comment);
  }

  private Comment findComment(Long commentId) {
    return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
  }
}
