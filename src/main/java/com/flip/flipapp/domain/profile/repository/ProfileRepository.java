package com.flip.flipapp.domain.profile.repository;

import com.flip.flipapp.domain.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

  boolean existsByUserId(String userId);
}
