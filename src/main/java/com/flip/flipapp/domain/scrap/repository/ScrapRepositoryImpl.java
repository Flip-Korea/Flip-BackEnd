package com.flip.flipapp.domain.scrap.repository;

import static com.flip.flipapp.domain.post.model.QPost.post;
import static com.flip.flipapp.domain.profile.model.QProfile.profile;
import static com.flip.flipapp.domain.scrap.model.QScrap.scrap;

import com.flip.flipapp.domain.scrap.repository.dto.QScrapPageDto;
import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageCondition;
import com.flip.flipapp.domain.scrap.repository.dto.ScrapPageDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class ScrapRepositoryImpl implements ScrapRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ScrapRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ScrapPageDto> findScrapsPage(ScrapPageCondition condition) {
    List<ScrapPageDto> content = queryFactory
        .select(
            new QScrapPageDto(
                scrap.scrapId,
                scrap.scrapAt,
                post.title,
                post.content,
                post.profile.nickname
            )
        )
        .from(scrap)
        .join(scrap.post, post)
        .join(post.profile, profile)
        .where(
            scrap.profile.profileId.eq(condition.profileId())
                                   .and(ltScrapId(condition.cursor()))
        )
        .orderBy(scrap.scrapId.desc())
        .limit(condition.limit())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
        .select(scrap.count())
        .from(scrap)
        .where(scrap.profile.profileId.eq(condition.profileId()));

    if (condition.cursor() == null) {
      return new PageImpl<>(content, Pageable.unpaged(), countQuery.fetchOne());
    } else {
      return new PageImpl<>(content, Pageable.unpaged(), 0);
    }
  }

  // cursor 보다 작은 scrapId를 조회
  private BooleanExpression ltScrapId(Long scrapId) {
    if (scrapId == null) {
      return null;
    }

    return scrap.scrapId.lt(scrapId);
  }

}
