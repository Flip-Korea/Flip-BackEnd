package com.flip.flipapp.domain.account.repository;

import com.flip.flipapp.domain.account.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByOauthId(String oauthId);
}
