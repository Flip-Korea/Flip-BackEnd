package com.flip.flipapp.domain.profile.controller;

import com.flip.flipapp.domain.profile.controller.dto.request.CheckUserIdRequest;
import com.flip.flipapp.domain.profile.service.CheckUserIdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckUserIdController {

  private final CheckUserIdService checkUserIdService;

  @PostMapping("/api/v1/validations/user-id")
  public ResponseEntity<Object> checkUserId(
      @RequestBody @Valid CheckUserIdRequest checkUserIdRequest) {
    checkUserIdService.checkUserId(checkUserIdRequest.userId());

    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
