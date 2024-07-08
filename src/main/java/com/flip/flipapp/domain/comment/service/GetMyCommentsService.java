package com.flip.flipapp.domain.comment.service;

import com.flip.flipapp.domain.comment.repository.CommentRepository;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentPageDto;
import com.flip.flipapp.domain.comment.service.dto.GetMyCommentsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMyCommentsService {

  private final CommentRepository commentRepository;

  @Transactional(readOnly = true)
  public Page<MyCommentPageDto> getMyComments(GetMyCommentsQuery query) {
    return commentRepository.findMyCommentsPage(query.toCondition());
  }
}
