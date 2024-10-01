package com.flip.flipapp.domain.account.controller;

import com.flip.flipapp.domain.account.controller.dto.request.OauthIdRequest;
import com.flip.flipapp.domain.account.controller.dto.response.GetSuspensionDetailsResponse;
import com.flip.flipapp.domain.account.service.GetSuspensionDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSuspensionDetailsController {

  private final GetSuspensionDetailsService getSuspensionDetailsService;

  @PostMapping("/api/v1/suspension-details")
  public ResponseEntity<GetSuspensionDetailsResponse> getSuspensionDetails(@RequestBody @Valid OauthIdRequest oauthIdRequest) {
    GetSuspensionDetailsResponse response = getSuspensionDetailsService.getSuspensionDetails(oauthIdRequest);

    return ResponseEntity.ok(response);
  }
}
