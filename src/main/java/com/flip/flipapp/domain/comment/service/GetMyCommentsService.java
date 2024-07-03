package com.flip.flipapp.domain.comment.service;

import com.flip.flipapp.domain.comment.repository.CommentRepository;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentPageDto;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentsPageCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyCommentsService {

  private final CommentRepository commentRepository;

  @Transactional(readOnly = true)
  public Page<MyCommentPageDto> getMyComments(Long profileId, Long lastCommentId) {
    MyCommentsPageCondition condition = new MyCommentsPageCondition(profileId,
        lastCommentId, 15);
    return commentRepository.findMyCommentsPage(condition);
  }
}
