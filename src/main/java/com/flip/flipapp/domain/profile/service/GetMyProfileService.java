package com.flip.flipapp.domain.profile.service;

import com.flip.flipapp.domain.profile.controller.dto.response.GetMyProfileResponse;
import com.flip.flipapp.domain.profile.exception.ProfileNotFoundException;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMyProfileService {

  private final ProfileRepository profileRepository;

  public GetMyProfileResponse getMyProfile(long profileId) {
    Profile myProfile = profileRepository.findById(profileId)
        .orElseThrow(ProfileNotFoundException::new);

    return GetMyProfileResponse.builder()
        .userId(myProfile.getUserId())
        .nickname(myProfile.getNickname())
        .introduce(myProfile.getIntroduce())
        .followerCnt(myProfile.getFollowerCnt())
        .followingCnt(myProfile.getFollowingCnt())
        .postCnt(myProfile.getPostCnt())
        .build();
  }
}
