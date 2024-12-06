package com.flip.flipapp.domain.follow.service;

import com.flip.flipapp.domain.follow.exception.FollowNotFoundException;
import com.flip.flipapp.domain.follow.exception.NotFollowerException;
import com.flip.flipapp.domain.follow.model.Follow;
import com.flip.flipapp.domain.follow.repository.FollowRepository;
import com.flip.flipapp.domain.follow.service.dto.UnfollowCommand;
import com.flip.flipapp.domain.profile.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnfollowService {

  private final FollowRepository followRepository;

  @Transactional
  public void unfollow(UnfollowCommand command) {
    Follow follow = findFollow(command.followId());

    if (!follow.isFollower(command.profileId())) {
      throw new NotFollowerException();
    }

    Profile following = follow.getFollowing();
    Profile follower = follow.getFollower();

    followRepository.delete(follow);

    following.decrementFollowerCnt();
    follower.decrementFollowingCnt();
  }

  private Follow findFollow(Long followId) {
    return followRepository.findById(followId).orElseThrow(FollowNotFoundException::new);
  }
}
