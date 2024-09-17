package com.flip.flipapp.global.security.jwt;

import static com.flip.flipapp.global.security.jwt.JwtType.ACCESS_TOKEN;
import static com.flip.flipapp.global.security.jwt.JwtType.REFRESH_TOKEN;

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

  public String createAccessToken(Long profileId) {
    return Jwts.builder()
        .setHeaderParam("typ", ACCESS_TOKEN.getType())
        .setIssuer(ISSUER)
        .setSubject(profileId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN.getExpiration()))
        .signWith(getSigningKey(SECRET_KEY))
        .compact();
  }

  public String createRefreshToken(Long profileId) {
    return Jwts.builder()
        .setHeaderParam("typ", REFRESH_TOKEN.getType())
        .setIssuer(ISSUER)
        .setSubject(profileId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN.getExpiration()))
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

  public void validateRefreshToken(String bearerToken) {
    String pureToken = bearerToken.substring("Bearer ".length());

    String tokenType = getTokenType(pureToken);
    if (!JwtType.REFRESH_TOKEN.getType().equals(tokenType)) {
      throw new CustomMalformedJwtException();
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

  private String getTokenType(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey(SECRET_KEY))
        .build()
        .parseClaimsJws(token)
        .getHeader()
        .getType();
  }

}
