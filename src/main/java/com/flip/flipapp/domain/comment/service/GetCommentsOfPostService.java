package com.flip.flipapp.domain.comment.service;

import com.flip.flipapp.domain.comment.repository.CommentRepository;
import com.flip.flipapp.domain.comment.repository.dto.CommentOfPostDto;
import com.flip.flipapp.domain.comment.service.dto.GetCommentsOfPostQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCommentsOfPostService {

  private final CommentRepository commentRepository;

  @Transactional(readOnly = true)
  public Page<CommentOfPostDto> getCommentsOfPost(GetCommentsOfPostQuery query) {
    return commentRepository.findCommentsPageByPostId(query.toCondition());
  }
}
