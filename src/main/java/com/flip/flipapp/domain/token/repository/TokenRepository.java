package com.flip.flipapp.domain.token.repository;

import com.flip.flipapp.domain.token.model.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

  Optional<Token> findByRefreshToken(String refreshToken);

}
