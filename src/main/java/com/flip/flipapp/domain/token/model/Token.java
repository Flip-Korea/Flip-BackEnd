package com.flip.flipapp.domain.token.model;

import com.flip.flipapp.domain.profile.model.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "token_id", nullable = false, columnDefinition = "bigint")
  private Long tokenId;

  @Column(name = "refresh_token", nullable = false, columnDefinition = "varchar(500)")
  private String refreshToken;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false)
  private Profile profile;

  public void updateRefreshToken(String newRefreshToken) {
    if (!this.refreshToken.equals(newRefreshToken)) {
      this.refreshToken = newRefreshToken;
    }
  }
}
