package com.flip.flipapp.domain.comment.repository;

import com.flip.flipapp.domain.comment.repository.dto.CommentOfPostDto;
import com.flip.flipapp.domain.comment.repository.dto.CommentsOfPostCondition;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentPageDto;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentsPageCondition;
import org.springframework.data.domain.Page;

public interface CommentRepositoryCustom {
  Page<MyCommentPageDto> findMyCommentsPage(MyCommentsPageCondition condition);
  Page<CommentOfPostDto> findCommentsPageByPostId(CommentsOfPostCondition condition);
}
