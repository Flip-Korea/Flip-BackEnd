package com.flip.flipapp.global.security.jwt;

import com.flip.flipapp.global.error.exception.CustomExpiredJwtException;
import com.flip.flipapp.global.error.exception.CustomIllegalArgumentException;
import com.flip.flipapp.global.error.exception.CustomMalformedJwtException;
import com.flip.flipapp.global.error.exception.CustomSignatureException;
import com.flip.flipapp.global.error.exception.CustomUnsupportedJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  private static final String ISSUER = "flip";
  private static final long ACCESS_TOKEN_EXPIRY_TIME = 1000L * 60 * 60 * 12;
  private static final long REFRESH_TOKEN_EXPIRY_TIME = 1000L * 60 * 60 * 24 * 14;

  public String createAccessToken(Long profileId) {
    return Jwts.builder()
        .setIssuer(ISSUER)
        .setSubject(profileId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY_TIME))
        .signWith(getSigningKey(SECRET_KEY))
        .compact();
  }

  public String createRefreshToken(Long profileId) {
    return Jwts.builder()
        .setIssuer(ISSUER)
        .setSubject(profileId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY_TIME))
        .signWith(getSigningKey(SECRET_KEY))
        .compact();
  }

  public void validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build().parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      throw new CustomExpiredJwtException();
    } catch (UnsupportedJwtException e) {
      throw new CustomUnsupportedJwtException();
    } catch (MalformedJwtException e) {
      throw new CustomMalformedJwtException();
    } catch (SignatureException e) {
      throw new CustomSignatureException();
    } catch (IllegalArgumentException e) {
      throw new CustomIllegalArgumentException();
    } catch (Exception e) {
      throw e;
    }
  }

  public String getSubjectFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey(SECRET_KEY)).build()
        .parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public Key getSigningKey(String secretKey) {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

}
