package com.flip.flipapp.domain.account.repository;

import com.flip.flipapp.domain.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByOauthId(String oauthId);
}
