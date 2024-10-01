package com.flip.flipapp.domain.blame.repository;

import static com.flip.flipapp.domain.blame.model.QBlame.blame;

import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.blame.model.BlameType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class BlameRepositoryImpl implements BlameRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public BlameRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Transactional(readOnly = true)
  @Override
  public List<String> findDistinctBlameTypesByReportedId(Account account) {
    return queryFactory
        .select(blame.type)
        .from(blame)
        .where(blame.reportedId.eq(account))
        .distinct()
        .fetch()
        .stream()
        .map(BlameType::getDescription)
        .toList();
  }
}
