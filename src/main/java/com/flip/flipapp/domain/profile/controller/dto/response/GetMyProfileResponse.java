package com.flip.flipapp.domain.profile.controller.dto.response;

import lombok.Builder;

@Builder
public record GetMyProfileResponse(
    String userId,
    String nickname,
    String introduce,
    String imageUrl,
    long followerCnt,
    long followingCnt,
    long postCnt
) {
}
