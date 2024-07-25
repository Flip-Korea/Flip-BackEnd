package com.flip.flipapp.domain.temp_post.repository;

import static com.flip.flipapp.domain.category.model.QCategory.category;
import static com.flip.flipapp.domain.temp_post.model.QTempPost.tempPost;

import com.flip.flipapp.domain.temp_post.model.TempPost;
import com.flip.flipapp.domain.temp_post.repository.dto.TempPostPageCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class TempPostRepositoryImpl implements TempPostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public TempPostRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<TempPost> findTempPostsPage(TempPostPageCondition condition) {
    List<TempPost> content = queryFactory
        .select(tempPost)
        .from(tempPost)
        .join(tempPost.category, category).fetchJoin()
        .where(
            tempPost.profile.profileId.eq(condition.profileId())
                                      .and(gtTempPostId(condition.cursor()))
        )
        .orderBy(tempPost.postId.asc())
        .limit(condition.limit())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(tempPost.count())
        .from(tempPost)
        .where(tempPost.profile.profileId.eq(condition.profileId()));

    if (condition.cursor() == null) {
      return new PageImpl<>(content, Pageable.unpaged(), countQuery.fetchOne());
    } else {
      return new PageImpl<>(content, Pageable.unpaged(), 0);
    }
  }

  private BooleanExpression gtTempPostId(Long tempPostId) {
    if (tempPostId == null) {
      return null;
    }

    return tempPost.postId.gt(tempPostId);
  }
}
