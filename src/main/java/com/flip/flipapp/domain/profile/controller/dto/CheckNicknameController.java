package com.flip.flipapp.domain.profile.controller.dto;

import com.flip.flipapp.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckNicknameController {

  private final ProfileService profileService;

  @GetMapping("/api/v1/profile/check/nickname/{nickname}")
  public ResponseEntity<Void> checkNickname(@PathVariable("nickname") String nickname) {
    final String NICKNAME_REGEX = "^[a-zA-Z0-9\\uAC00-\\uD7A3ㄱ-ㅎㅏ-ㅣ]{1,12}$";

    if (!nickname.matches(NICKNAME_REGEX)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      return ResponseEntity.status(HttpStatus.OK).build();
    }
  }

}