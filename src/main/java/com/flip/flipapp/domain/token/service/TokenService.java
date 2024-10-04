package com.flip.flipapp.domain.token.service;

import com.flip.flipapp.domain.account.controller.dto.response.JwtResponse;
import com.flip.flipapp.domain.profile.model.Profile;
import com.flip.flipapp.domain.token.model.Token;
import com.flip.flipapp.domain.token.repository.TokenRepository;
import com.flip.flipapp.global.error.exception.CustomMalformedJwtException;
import com.flip.flipapp.global.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenRepository tokenRepository;
  private final JwtProvider jwtProvider;


  public JwtResponse createTokens(Long profileId) {
    String newAccessToken = jwtProvider.createAccessToken(profileId);
    String newRefreshToken = jwtProvider.createRefreshToken(profileId);

    return new JwtResponse(newAccessToken, newRefreshToken);
  }

  public void saveRefreshToken(Profile profile, String newRefreshToken) {
    Token token = Token.builder()
        .profile(profile)
        .refreshToken(newRefreshToken)
        .build();

    tokenRepository.save(token);
  }

  public String parseBearerToken(String bearerToken) {
    return bearerToken.replace("Bearer ", "");
  }

  public Token validateToken(String pureRefreshToken) {
    return tokenRepository.findByRefreshToken(pureRefreshToken)
        .orElseThrow(CustomMalformedJwtException::new);
  }

  public JwtResponse createAndSaveTokens(Profile profile) {
    JwtResponse jwtResponse = createTokens(profile.getProfileId());
    saveRefreshToken(profile, jwtResponse.refreshToken());

    return jwtResponse;
  }

  @Transactional
  public JwtResponse reissue(String currentRefreshToken, Long profileId) {
    String pureRefreshToken = parseBearerToken(currentRefreshToken);
    Token token = validateToken(pureRefreshToken);
    JwtResponse jwtResponse = createTokens(profileId);

    token.updateRefreshToken(jwtResponse.refreshToken());
    return jwtResponse;
  }
}

