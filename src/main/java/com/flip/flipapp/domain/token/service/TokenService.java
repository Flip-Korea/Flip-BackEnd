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

  @Transactional
  public void saveRefreshToken(Profile profile, String newRefreshToken) {
    Token token = Token.builder()
        .profile(profile)
        .refreshToken(newRefreshToken)
        .build();

    tokenRepository.save(token);
  }

  @Transactional
  public Token validateToken(String currentRefreshToken) {
    String pureRefreshToken = currentRefreshToken.replace("Bearer ", "");

    return tokenRepository.findByRefreshToken(pureRefreshToken)
        .orElseThrow(CustomMalformedJwtException::new);
  }

  @Transactional
  public void updateRefreshToken(Token token, String newRefreshToken) {
    token.updateRefreshToken(newRefreshToken);
  }

  public JwtResponse createAndSaveTokens(Profile profile) {
    JwtResponse jwtResponse = createTokens(profile.getProfileId());
    saveRefreshToken(profile, jwtResponse.refreshToken());

    return jwtResponse;
  }

  @Transactional
  public JwtResponse validateAndCreateTokens(String currentRefreshToken, Long profileId) {
    Token token = validateToken(currentRefreshToken);
    JwtResponse jwtResponse = createTokens(profileId);
    updateRefreshToken(token, jwtResponse.refreshToken());

    return jwtResponse;
  }
}

