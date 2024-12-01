package com.flip.flipapp.domain.follow.service;

import com.flip.flipapp.domain.follow.exception.DuplicatedFollowException;
import com.flip.flipapp.domain.follow.exception.SelfFollowException;
import com.flip.flipapp.domain.follow.model.Follow;
import com.flip.flipapp.domain.follow.repository.FollowRepository;
import com.flip.flipapp.domain.follow.service.dto.FollowCommand;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

  private final FollowRepository followRepository;
  private final ProfileRepository profileRepository;

  @Transactional
  public Follow follow(FollowCommand command) {
    Profile follow = findFollow(command.followId());
    Profile follower = findFollow(command.followerId());

    if (isSelfFollow(follow, follower)) {
      throw new SelfFollowException();
    }

    if (followRepository.existsByFollowingAndFollower(follow, follower)) {
      throw new DuplicatedFollowException();
    }

    Follow newFollow = Follow.builder()
                             .following(follow)
                             .follower(follower)
                             .build();

    // 유니크 키 제약 조건에 의해 중복된 팔로우가 발생한 경우
    try {
      Follow savedFollow = followRepository.saveAndFlush(newFollow);
      follow.incrementFollowerCnt();
      follower.incrementFollowingCnt();
      return savedFollow;
    } catch (DataIntegrityViolationException e) {
      throw new DuplicatedFollowException();
    }
  }

  private boolean isSelfFollow(Profile follow, Profile follower) {
    return follow.getProfileId().equals(follower.getProfileId());
  }

  private Profile findFollow(Long profileId) {
    return profileRepository.findById(profileId).orElseThrow(ProfileNotFoundException::new);
  }
}
