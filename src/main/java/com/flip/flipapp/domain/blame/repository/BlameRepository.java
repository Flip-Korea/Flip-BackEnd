package com.flip.flipapp.domain.blame.repository;

import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.blame.model.Blame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlameRepository extends JpaRepository<Blame, Long> {

  List<Blame> findByReportedId(Account reportedId);
}
