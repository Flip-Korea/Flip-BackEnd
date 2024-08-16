package com.flip.flipapp.domain.follow.repository;

import com.flip.flipapp.domain.follow.model.Follow;
import com.flip.flipapp.domain.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

  boolean existsByFollowingAndFollower(Profile following, Profile follower);
}
