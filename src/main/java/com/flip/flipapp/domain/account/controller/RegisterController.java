package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.request.RegisterRequest;
import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.account.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService joinService;

  @PostMapping("/api/v1/account/register")
  public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest) {
    JwtResponse response = joinService.register(registerRequest);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
