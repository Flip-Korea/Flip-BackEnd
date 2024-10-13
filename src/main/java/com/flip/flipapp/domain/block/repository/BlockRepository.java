package com.flip.flipapp.domain.block.repository;

import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.block.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {

  boolean existsByBlockerAndBlocked(Account blocker, Account blocked);
}
