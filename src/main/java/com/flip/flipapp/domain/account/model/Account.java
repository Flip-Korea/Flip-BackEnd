package com.flip.flipapp.domain.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id", nullable = false, columnDefinition = "bigint")
  private Long accountId;

  @Column(name = "oauth_id", nullable = false, columnDefinition = "varchar(255)", unique = true)
  private String oauthId;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_state", nullable = false, columnDefinition = "varchar(25)")
  private AccountState accountState = AccountState.ACTIVE;

  @Column(name = "recent_login", nullable = false, columnDefinition = "bigint")
  private Long recentLogin;

  @Column(name = "suspended_at", columnDefinition = "datetime")
  private LocalDateTime suspendedAt;

}