package com.flip.flipapp.domain.profile.repository;

import com.flip.flipapp.domain.account.model.Account;
import com.flip.flipapp.domain.profile.model.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

  boolean existsByUserId(String userId);

  @Query("SELECT p.account FROM Profile p WHERE p.profileId = :profileId")
  Optional<Account> findAccountByProfileId(@Param("profileId") Long profileId);
}
