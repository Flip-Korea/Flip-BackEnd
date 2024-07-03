package com.flip.flipapp.domain.comment.repository;

import static com.flip.flipapp.domain.comment.model.QComment.comment;
import static com.flip.flipapp.domain.post.model.QPost.post;
import static com.flip.flipapp.domain.profile.model.QProfile.profile;

import com.flip.flipapp.domain.comment.repository.dto.MyCommentPageDto;
import com.flip.flipapp.domain.comment.repository.dto.MyCommentsPageCondition;
import com.flip.flipapp.domain.comment.repository.dto.QMyCommentPageDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public CommentRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  // no offset 방식의 페이징
  @Transactional(readOnly = true)
  @Override
  public Page<MyCommentPageDto> findMyCommentsPage(MyCommentsPageCondition condition) {
    List<MyCommentPageDto> content = queryFactory
        .select(new QMyCommentPageDto(
            post.postId,
            post.profile.nickname,
            post.title,
            comment.commentId,
            comment.content,
            comment.commentAt
        ))
        .from(comment)
        .join(comment.post, post)
        .join(post.profile, profile)
        .where(
            comment.profile.profileId.eq(condition.profileId())
                                     .and(ltCommentId(condition.CommentId())))
        .orderBy(comment.commentId.desc())
        .limit(condition.pageSize())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(comment.count())
        .from(comment)
        .where(comment.profile.profileId.eq(condition.profileId()));

    if (condition.CommentId() == null) {
      return new PageImpl<>(content, Pageable.unpaged(), countQuery.fetchOne());
    } else {
      return new PageImpl<>(content, Pageable.unpaged(), 0);
    }
  }


  // 요청 댓글이 첫 페이지일 경우 조건 제외
  private BooleanExpression ltCommentId(Long commentId) {
    if (commentId == null) {
      return null;
    }

    return comment.commentId.lt(commentId);
  }


}
