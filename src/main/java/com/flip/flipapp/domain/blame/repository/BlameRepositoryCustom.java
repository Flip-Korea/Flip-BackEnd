package com.flip.flipapp.domain.blame.repository;

import com.flip.flipapp.domain.account.model.Account;
import java.util.List;

public interface BlameRepositoryCustom {

  List<String> findDistinctBlameTypesByReportedId(Account account);

}
