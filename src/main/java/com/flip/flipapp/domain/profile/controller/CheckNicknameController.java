package com.flip.flipapp.domain.profile.controller;

import com.flip.flipapp.domain.profile.controller.dto.request.CheckNicknameRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckNicknameController {

  @PostMapping("/api/v1/validations/nickname")
  public ResponseEntity<Void> checkNickname(@RequestBody @Valid CheckNicknameRequest request) {
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
